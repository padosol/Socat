package com.userservice.domain.auth.service;

import com.ctc.wstx.util.StringUtil;
import com.userservice.domain.auth.dto.response.AuthDto;
import com.userservice.domain.auth.exception.AccessTokenNotMatchException;
import com.userservice.domain.auth.exception.RefreshTokenNotMatchException;
import com.userservice.domain.auth.service.usecase.RefreshUseCase;
import com.userservice.global.utils.JwtProvider;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RefreshTokenService implements RefreshUseCase {

    private final RedisTemplate<String, Object> redisTemplate;
    private final JwtProvider jwtProvider;

    private HashOperations<String, Object, Object> redisHash;

    @PostConstruct
    public void init() {
        this.redisHash = redisTemplate.opsForHash();
        redisTemplate.expire(jwtProvider.REFRESH_TOKEN_NAME, jwtProvider.getRefreshTokenExpiredTime(), TimeUnit.MILLISECONDS);
    }

    @Override
    public String createRefreshToken(String accessToken) {

        String refreshToken = jwtProvider.generateRefreshToken(accessToken);

        redisHash.put(jwtProvider.REFRESH_TOKEN_NAME, refreshToken, accessToken);

        return refreshToken;
    }


    @Override
    public AuthDto refresh(String refreshToken, String accessToken) {

        if(jwtProvider.validateToken(refreshToken)) {
            if(!redisHash.hasKey(jwtProvider.REFRESH_TOKEN_NAME, refreshToken)) {
                // token 일치하지 않습니다.
                throw new RefreshTokenNotMatchException(HttpStatus.BAD_REQUEST, "유효하지 않은 Refresh Token 입니다.");
            }

            String expired_access_token = (String)redisHash.get(jwtProvider.REFRESH_TOKEN_NAME, refreshToken);

            if (StringUtils.hasText(expired_access_token) && expired_access_token.equals(accessToken) ){

                // 새로발급시 refresh token 초기화 RTR 정책
                String userEmail = jwtProvider.getUserEmail(refreshToken);

                String new_access_token = jwtProvider.generateAccessToken(userEmail);
                String new_refresh_token = jwtProvider.generateRefreshToken(new_access_token);

                redisHash.delete(jwtProvider.REFRESH_TOKEN_NAME, refreshToken);
                redisHash.put(jwtProvider.REFRESH_TOKEN_NAME, new_refresh_token, new_access_token);

                return new AuthDto(new_access_token, new_refresh_token);
            }

            throw new AccessTokenNotMatchException(HttpStatus.BAD_REQUEST, "유효하지 않은 Access Token 입니다.");
        }

        throw new RefreshTokenNotMatchException(HttpStatus.BAD_REQUEST, "유효하지 않는 Refresh Token 입니다.");
    }
}
