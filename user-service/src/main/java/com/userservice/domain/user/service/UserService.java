package com.userservice.domain.user.service;

import com.userservice.application.ports.input.CreateUserUseCase;
import com.userservice.domain.user.User;
import com.userservice.infrastructure.apdapters.input.rest.dto.response.UserResponse;
import org.springframework.stereotype.Service;

@Service
public class UserService implements CreateUserUseCase {


    @Override
    public UserResponse createUser(User user) {
        return null;
    }
}
