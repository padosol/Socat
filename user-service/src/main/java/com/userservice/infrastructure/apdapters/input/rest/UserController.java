package com.userservice.infrastructure.apdapters.input.rest;


import com.userservice.application.ports.input.CreateUserUseCase;
import com.userservice.infrastructure.apdapters.input.rest.dto.request.CreateUserDTO;
import com.userservice.infrastructure.apdapters.input.rest.dto.response.UserResponse;
import com.userservice.infrastructure.apdapters.input.rest.mapper.UserDtoMapper;
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

        UserResponse user = createUserUseCase.createUser(UserDtoMapper.toUser(createUserDTO));

        return ResponseEntity.status(201).body(user);
    }

}


















