package com.userservice.domain.user.controller.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Getter
@Builder
public class UserResponse {
    private String username;
    private String id;
    private String email;
    private Set<String> authorities;
}
