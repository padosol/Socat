package com.userservice.domain.user.service.usecase;

import com.userservice.domain.user.controller.dto.response.UserResponse;

public interface GetUserUseCase {
    UserResponse findUserByEmail(String email);

    UserResponse findUserById(String id);
}
