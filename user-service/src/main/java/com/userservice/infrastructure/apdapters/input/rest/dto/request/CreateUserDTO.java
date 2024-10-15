package com.userservice.infrastructure.apdapters.input.rest.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreateUserDTO {

    @NotBlank(message = "이름을 입력해주세요.")
    @Size(min = 2, max = 4, message = "이름은 2자 이상 4자 이하입니다.")
    private String userName;

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min = 8, max = 16, message = "비밀번호는 8자 이상 16자 이하 입니다.")
    private String password;
}
