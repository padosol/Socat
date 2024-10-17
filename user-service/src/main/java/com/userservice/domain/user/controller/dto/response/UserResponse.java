package com.userservice.domain.user.controller.dto.response;

import lombok.Builder;

@Builder
public class UserResponse {
    private String userName;
    private String id;
    private String email;
}
