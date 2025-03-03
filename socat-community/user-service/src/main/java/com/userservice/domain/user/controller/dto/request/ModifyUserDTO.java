package com.userservice.domain.user.controller.dto.request;

import lombok.Getter;

@Getter
public class ModifyUserDTO {
    private String username;
    private String email;
    private String password;

    public ModifyUserDTO() {}

    public ModifyUserDTO(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

}
