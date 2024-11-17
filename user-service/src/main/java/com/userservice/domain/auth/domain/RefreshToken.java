package com.userservice.domain.auth.domain;

import lombok.Getter;

@Getter
public class RefreshToken {
    private String token;

    private RefreshToken(){};

    public RefreshToken(String token) {
        this.token = token;
    }
}
