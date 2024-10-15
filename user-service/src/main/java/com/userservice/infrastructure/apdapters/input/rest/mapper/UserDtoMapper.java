package com.userservice.infrastructure.apdapters.input.rest.mapper;

import com.userservice.domain.user.User;
import com.userservice.infrastructure.apdapters.input.rest.dto.request.CreateUserDTO;

public class UserDtoMapper {
    public static User toUser(CreateUserDTO createUserDTO) {

        return User.builder()
                .userName(createUserDTO.getUserName())
                .password(createUserDTO.getPassword())
                .email(createUserDTO.getEmail())
                .build();
    }
}
