package com.userservice.domain.user.controller;


import com.userservice.domain.user.entity.User;
import com.userservice.domain.user.service.CreateUserUseCase;
import com.userservice.domain.user.controller.dto.request.CreateUserDTO;
import com.userservice.domain.user.controller.dto.response.UserResponse;
import com.userservice.domain.user.controller.mapper.UserDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {

    private final CreateUserUseCase createUserUseCase;


    @PostMapping("/users")
    public ResponseEntity<UserResponse> createUser(
            @RequestBody  CreateUserDTO createUserDTO
    ) {

        UserResponse user = createUserUseCase.createUser(
                User.builder()
                    .userName(createUserDTO.getUserName())
                    .email(createUserDTO.getEmail())
                    .password(createUserDTO.getPassword())
                    .build()
        );

        return ResponseEntity.status(201).body(user);
    }

}


















