package com.userservice.domain.auth.dto.request;

import lombok.Getter;

@Getter
public class LoginDto {

    private String email;
    private String password;
}
