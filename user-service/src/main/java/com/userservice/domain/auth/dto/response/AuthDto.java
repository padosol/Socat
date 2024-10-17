package com.userservice.domain.auth.dto.response;

import lombok.Getter;

@Getter
public class AuthDto {
    private String jwt;
    public AuthDto(String jwt) {
        this.jwt = jwt;
    }
}
