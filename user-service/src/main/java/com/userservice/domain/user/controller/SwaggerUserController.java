package com.userservice.domain.user.controller;

import com.userservice.domain.user.controller.dto.response.UserResponse;
import org.springframework.http.ResponseEntity;

public interface SwaggerUserController {
    ResponseEntity<UserResponse> getUserByUserId(String userId);
}
