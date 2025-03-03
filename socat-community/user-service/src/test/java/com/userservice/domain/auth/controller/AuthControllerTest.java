package com.userservice.domain.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.userservice.domain.auth.dto.request.LoginDto;
import com.userservice.domain.auth.dto.request.TokenDto;
import com.userservice.domain.auth.dto.response.AuthDto;
import com.userservice.domain.auth.service.usecase.AccessUseCase;
import com.userservice.domain.auth.service.usecase.LogoutUseCase;
import com.userservice.domain.auth.service.usecase.RefreshUseCase;
import com.userservice.global.config.security.JwtFilter;
import com.userservice.global.config.security.SecurityConfig;
import com.userservice.global.exception.CustomException;
import com.userservice.global.utils.JwtProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
        controllers = AuthController.class,
        excludeAutoConfiguration = SecurityAutoConfiguration.class, // 추가
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)}
)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthenticationManagerBuilder authenticationManagerBuilder;

    @MockBean
    private RefreshUseCase refreshUseCase;

    @MockBean
    private LogoutUseCase logoutUseCase;

    @MockBean
    private AccessUseCase accessUseCase;

    @MockBean
    private JwtProvider jwtProvider;

    private LoginDto loginDto;
    private TokenDto tokenDto;
    private AuthDto authDto;
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        loginDto = new LoginDto("test@example.com", "password");

        tokenDto = new TokenDto("test.access.token", "test.refresh.token");

        authDto = new AuthDto("new.access.token", "new.refresh.token");

        authentication = new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());
    }

    @Test
    @DisplayName("로그인 성공 테스트")
    void authorizeSuccess() throws Exception {
        // Given
        when(authenticationManagerBuilder.getObject().authenticate(any()))
                .thenReturn(authentication);
        when(accessUseCase.createAccessToken(any()))
                .thenReturn("test.access.token");
        when(refreshUseCase.createRefreshToken(any()))
                .thenReturn("test.refresh.token");

        // When & Then
        mockMvc.perform(post("/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginDto)))
                .andExpect(status().isOk())
                .andExpect(header().exists(JwtFilter.AUTHORIZATION_HEADER))
                .andExpect(jsonPath("$.accessToken").value("test.access.token"))
                .andExpect(jsonPath("$.refreshToken").value("test.refresh.token"));
    }

    @Test
    @DisplayName("토큰 갱신 성공 테스트")
    void refreshAuthorizeSuccess() throws Exception {
        // Given
        when(refreshUseCase.refresh(anyString(), anyString())).thenReturn(authDto);

        // When & Then
        mockMvc.perform(post("/refresh-auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(tokenDto)))
                .andExpect(status().isOk())
                .andExpect(header().string(JwtFilter.AUTHORIZATION_HEADER, "Bearer new.access.token"))
                .andExpect(jsonPath("$.data.accessToken").value("new.access.token"))
                .andExpect(jsonPath("$.data.refreshToken").value("new.refresh.token"));
    }

    @Test
    @DisplayName("토큰 갱신 실패 테스트")
    void refreshAuthorizeFail() throws Exception {
        // Given
        when(refreshUseCase.refresh(anyString(), anyString()))
                .thenThrow(new CustomException(HttpStatus.BAD_REQUEST, ""));

        // When & Then
        mockMvc.perform(post("/refresh-auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(tokenDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    @DisplayName("로그아웃 성공 테스트")
    void logoutSuccess() throws Exception {
        // Given
        doNothing().when(logoutUseCase).logout(any(TokenDto.class));

        // When & Then
        mockMvc.perform(post("/logout")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(tokenDto)))
                .andExpect(status().isOk());

        verify(logoutUseCase, times(1)).logout(any(TokenDto.class));
    }

    @Test
    @DisplayName("액세스 토큰 검증 테스트")
    void accessTokenAuthTest() throws Exception {
        // Given
        when(jwtProvider.resolveToken(any())).thenReturn("test.access.token");
        when(jwtProvider.validateToken(anyString())).thenReturn(true);

        // When & Then
        mockMvc.perform(post("/auth/accessToken"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").value(true));
    }
}