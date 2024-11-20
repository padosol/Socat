package com.userservice.domain.auth.dto.request;

import lombok.Getter;

@Getter
public class TokenDto {
    private String accessToken;
    private String refreshToken;
}
