package com.userservice.domain.user.service;

import com.userservice.domain.user.controller.dto.response.UserResponse;
import com.userservice.domain.user.entity.User;

public interface CreateUserUseCase {
    UserResponse createUser(User user);
}
