package com.userservice.domain.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class LoginDto {

    @Schema(description = "유저 이메일", example = "test@test.com")
    private String email;
    @Schema(description = "유저 패스워드", example = "test1234")
    private String password;
}
