package com.userservice.domain.auth.dto.response;

import lombok.Getter;

@Getter
public class AuthDto {
    private String accessToken;
    private String refreshToken;

    private AuthDto(){};

    public AuthDto(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
