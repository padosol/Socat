package com.userservice.domain.user.controller.mapper;

import com.userservice.domain.user.User;
import com.userservice.domain.user.controller.dto.request.CreateUserDTO;
import com.userservice.domain.user.controller.dto.response.UserResponse;

public class UserDtoMapper {
    public static User toUser(CreateUserDTO createUserDTO) {

        return User.builder()
                .userName(createUserDTO.getUserName())
                .password(createUserDTO.getPassword())
                .email(createUserDTO.getEmail())
                .build();
    }

    public static UserResponse toDTO(User user) {
        return new UserResponse();
    }
}
