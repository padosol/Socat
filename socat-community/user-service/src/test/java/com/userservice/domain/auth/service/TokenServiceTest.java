package com.userservice.domain.auth.service;

import com.userservice.domain.auth.dto.request.TokenDto;
import com.userservice.domain.auth.dto.response.AuthDto;
import com.userservice.domain.auth.exception.AccessTokenNotMatchException;
import com.userservice.domain.auth.exception.RefreshTokenNotMatchException;
import com.userservice.domain.user.entity.UserEntity;
import com.userservice.domain.user.repository.UserJpaRepository;
import com.userservice.global.utils.JwtProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TokenServiceTest {

    @InjectMocks
    private TokenService tokenService;

    @Mock
    private UserJpaRepository userJpaRepository;

    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    @Mock
    private JwtProvider jwtProvider;

    @Mock
    private HashOperations<String, Object, Object> hashOperations;

    private static final String TEST_ACCESS_TOKEN = "test.access.token";
    private static final String TEST_REFRESH_TOKEN = "test.refresh.token";
    private static final String TEST_EMAIL = "test@example.com";
    private static final String TEST_USER_ID = "test-user-id";

    @BeforeEach
    void setUp() {
        when(redisTemplate.opsForHash()).thenReturn(hashOperations);
        tokenService.init();

        verify(redisTemplate).expire(eq(jwtProvider.REFRESH_TOKEN_NAME), anyLong(), eq(TimeUnit.MILLISECONDS));
        verify(redisTemplate).expire(eq(jwtProvider.BLACK_LIST), anyLong(), eq(TimeUnit.MILLISECONDS));
        verify(redisTemplate).expire(eq(jwtProvider.ACTIVE_USER), anyLong(), eq(TimeUnit.MILLISECONDS));
    }

    @Test
    @DisplayName("createRefreshToken: 리프레시 토큰 생성 성공")
    void createRefreshToken_Success() {
        // Given
        when(jwtProvider.generateRefreshToken(TEST_ACCESS_TOKEN)).thenReturn(TEST_REFRESH_TOKEN);

        // When
        String result = tokenService.createRefreshToken(TEST_ACCESS_TOKEN);

        // Then
        assertThat(result).isEqualTo(TEST_REFRESH_TOKEN);
        verify(hashOperations).put(jwtProvider.REFRESH_TOKEN_NAME, TEST_REFRESH_TOKEN, TEST_ACCESS_TOKEN);
    }

    @Test
    @DisplayName("refresh: 토큰 갱신 성공")
    void refresh_Success() {
        // Given
        String newAccessToken = "new.access.token";
        String newRefreshToken = "new.refresh.token";

        when(jwtProvider.validateToken(TEST_REFRESH_TOKEN)).thenReturn(true);
        when(hashOperations.hasKey(jwtProvider.REFRESH_TOKEN_NAME, TEST_REFRESH_TOKEN)).thenReturn(true);
        when(hashOperations.get(jwtProvider.REFRESH_TOKEN_NAME, TEST_REFRESH_TOKEN)).thenReturn(TEST_ACCESS_TOKEN);
        when(jwtProvider.getUserEmail(TEST_REFRESH_TOKEN)).thenReturn(TEST_EMAIL);
        when(jwtProvider.generateAccessToken(TEST_EMAIL)).thenReturn(newAccessToken);
        when(jwtProvider.generateRefreshToken(newAccessToken)).thenReturn(newRefreshToken);

        // When
        AuthDto result = tokenService.refresh(TEST_REFRESH_TOKEN, TEST_ACCESS_TOKEN);

        // Then
        assertThat(result.getAccessToken()).isEqualTo(newAccessToken);
        assertThat(result.getRefreshToken()).isEqualTo(newRefreshToken);
        verify(hashOperations).delete(jwtProvider.REFRESH_TOKEN_NAME, TEST_REFRESH_TOKEN);
        verify(hashOperations).put(jwtProvider.REFRESH_TOKEN_NAME, newRefreshToken, newAccessToken);
    }

    @Test
    @DisplayName("refresh: 유효하지 않은 리프레시 토큰으로 실패")
    void refresh_InvalidRefreshToken() {
        // Given
        when(jwtProvider.validateToken(TEST_REFRESH_TOKEN)).thenReturn(false);

        // When & Then
        assertThrows(RefreshTokenNotMatchException.class, () ->
            tokenService.refresh(TEST_REFRESH_TOKEN, TEST_ACCESS_TOKEN)
        );
    }

    @Test
    @DisplayName("refresh: 저장소에 없는 리프레시 토큰으로 실패")
    void refresh_NonExistentRefreshToken() {
        // Given
        when(jwtProvider.validateToken(TEST_REFRESH_TOKEN)).thenReturn(true);
        when(hashOperations.hasKey(jwtProvider.REFRESH_TOKEN_NAME, TEST_REFRESH_TOKEN)).thenReturn(false);

        // When & Then
        assertThrows(RefreshTokenNotMatchException.class, () ->
            tokenService.refresh(TEST_REFRESH_TOKEN, TEST_ACCESS_TOKEN)
        );
    }

    @Test
    @DisplayName("refresh: 액세스 토큰 불일치로 실패")
    void refresh_MismatchedAccessToken() {
        // Given
        when(jwtProvider.validateToken(TEST_REFRESH_TOKEN)).thenReturn(true);
        when(hashOperations.hasKey(jwtProvider.REFRESH_TOKEN_NAME, TEST_REFRESH_TOKEN)).thenReturn(true);
        when(hashOperations.get(jwtProvider.REFRESH_TOKEN_NAME, TEST_REFRESH_TOKEN)).thenReturn("different.token");

        // When & Then
        assertThrows(AccessTokenNotMatchException.class, () ->
            tokenService.refresh(TEST_REFRESH_TOKEN, TEST_ACCESS_TOKEN)
        );
    }

    @Test
    @DisplayName("logout: 로그아웃 성공")
    void logout_Success() {
        // Given
        TokenDto tokenDto = new TokenDto(TEST_ACCESS_TOKEN, TEST_REFRESH_TOKEN);

        // When
        tokenService.logout(tokenDto);

        // Then
        verify(hashOperations).put(jwtProvider.BLACK_LIST, TEST_ACCESS_TOKEN, TEST_ACCESS_TOKEN);
        verify(hashOperations).delete(jwtProvider.REFRESH_TOKEN_NAME, TEST_REFRESH_TOKEN);
    }

    @Test
    @DisplayName("createAccessToken: 새로운 액세스 토큰 생성 성공")
    void createAccessToken_Success() {
        // Given
        Authentication authentication = mock(Authentication.class);
        UserEntity user = UserEntity.builder()
                .id(TEST_USER_ID)
                .email(TEST_EMAIL)
                .build();

        when(authentication.getName()).thenReturn(TEST_EMAIL);
        when(userJpaRepository.findById(TEST_EMAIL)).thenReturn(Optional.of(user));
        when(jwtProvider.createJwt(authentication)).thenReturn(TEST_ACCESS_TOKEN);

        // When
        String result = tokenService.createAccessToken(authentication);

        // Then
        assertThat(result).isEqualTo(TEST_ACCESS_TOKEN);
        verify(hashOperations).put(jwtProvider.ACTIVE_USER, TEST_USER_ID, TEST_ACCESS_TOKEN);
    }

    @Test
    @DisplayName("createAccessToken: 기존 토큰이 있는 경우 블랙리스트 처리 후 새 토큰 생성")
    void createAccessToken_WithExistingToken() {
        // Given
        Authentication authentication = mock(Authentication.class);
        UserEntity user = UserEntity.builder()
                .id(TEST_USER_ID)
                .email(TEST_EMAIL)
                .build();
        String oldToken = "old.access.token";

        when(authentication.getName()).thenReturn(TEST_EMAIL);
        when(userJpaRepository.findById(TEST_EMAIL)).thenReturn(Optional.of(user));
        when(hashOperations.get(jwtProvider.ACTIVE_USER, TEST_USER_ID)).thenReturn(oldToken);
        when(jwtProvider.createJwt(authentication)).thenReturn(TEST_ACCESS_TOKEN);

        // When
        String result = tokenService.createAccessToken(authentication);

        // Then
        assertThat(result).isEqualTo(TEST_ACCESS_TOKEN);
        verify(hashOperations).delete(jwtProvider.ACTIVE_USER, TEST_USER_ID);
        verify(hashOperations).put(jwtProvider.BLACK_LIST, oldToken, oldToken);
        verify(hashOperations).put(jwtProvider.ACTIVE_USER, TEST_USER_ID, TEST_ACCESS_TOKEN);
    }

    @Test
    @DisplayName("createAccessToken: 존재하지 않는 사용자로 실패")
    void createAccessToken_UserNotFound() {
        // Given
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn(TEST_EMAIL);
        when(userJpaRepository.findById(TEST_EMAIL)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(UsernameNotFoundException.class, () ->
            tokenService.createAccessToken(authentication)
        );
    }
}