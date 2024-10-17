package com.userservice.domain.user.service;

import com.userservice.domain.user.repository.UserOutputPort;
import com.userservice.domain.user.User;
import com.userservice.domain.user.controller.dto.response.UserResponse;
import com.userservice.domain.user.controller.mapper.UserDtoMapper;
import com.userservice.domain.user.repository.mapper.UserEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements CreateUserUseCase, ModifyUserUserCase, RemoveUserUserCase {

    private final UserOutputPort userOutputPort;

    @Override
    public UserResponse createUser(User user) {

        User saveUser = userOutputPort.save(UserEntityMapper.toEntity(user));

        return UserDtoMapper.toDTO(saveUser);
    }

    @Override
    public UserResponse modifyUser(User user) {

        User saveUser = userOutputPort.save(UserEntityMapper.toEntity(user));

        return UserDtoMapper.toDTO(saveUser);
    }

    @Override
    public void removeUser(User user) {



    }
}
