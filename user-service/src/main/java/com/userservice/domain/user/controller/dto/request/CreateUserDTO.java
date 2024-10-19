package com.userservice.domain.user.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreateUserDTO {

    @NotBlank(message = "이름을 입력해주세요.")
    @Size(min = 2, max = 4, message = "이름은 2자 이상 4자 이하입니다.")
    @Schema(description = "유저 아이디", example = "test")
    private String userName;

    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식이 아닙니다.")
    @Schema(description = "유저 이메일", example = "test@test.com")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min = 8, max = 16, message = "비밀번호는 8자 이상 16자 이하 입니다.")
    @Schema(description = "유저 패스워드", example = "test1234")
    private String password;
}
