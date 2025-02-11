package com.userservice.domain.user.controller;


import com.userservice.domain.user.controller.dto.request.ModifyUserDTO;
import com.userservice.domain.user.entity.UserEntity;
import com.userservice.domain.user.service.usecase.CreateUserUseCase;
import com.userservice.domain.user.controller.dto.request.CreateUserDTO;
import com.userservice.domain.user.controller.dto.response.UserResponse;
import com.userservice.domain.user.service.usecase.GetUserUseCase;
import com.userservice.domain.user.service.usecase.ModifyUserUserCase;
import com.userservice.global.dto.APIResponse;
import com.userservice.global.utils.JwtProvider;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User", description = "User API")
@RestController
@RequiredArgsConstructor
public class UserController implements SwaggerUserController {

    private final GetUserUseCase getUserUseCase;
    private final CreateUserUseCase createUserUseCase;
    private final JwtProvider jwtProvider;
    private final ModifyUserUserCase modifyUserUserCase;


    @PostMapping("/users")
    public ResponseEntity<UserResponse> createUser(
            @RequestBody @Valid CreateUserDTO createUserDTO
    ) {
        UserResponse user = createUserUseCase.createUser(
                UserEntity.builder()
                    .userName(createUserDTO.getUsername())
                    .email(createUserDTO.getEmail())
                    .password(createUserDTO.getPassword())
                    .build()
        );

        return ResponseEntity.status(201).body(user);
    }
    
    @Override
    @GetMapping("/users")
    public ResponseEntity<UserResponse> getUser(
            HttpServletRequest request
    ) {
        String userId = jwtProvider.getUserId(request);

        UserResponse userResponse = getUserUseCase.findUserById(userId);

        return ResponseEntity.status(200).body(userResponse);
    }

    @Override
    @GetMapping("/users/{userId}")
    public ResponseEntity<APIResponse<UserResponse>> getUserByUserId(
            @PathVariable(value = "userId") String userId
    ) {
        UserResponse userResponse = getUserUseCase.findUserById(userId);

        return ResponseEntity.status(200).body(APIResponse.ok(userResponse));
    }


    // 유저 수정
    @Override
    @PutMapping("/users/{userId}")
    public ResponseEntity<APIResponse<UserResponse>> modifyUser(
            @PathVariable(value = "userId") String userId,
            @RequestBody @Valid ModifyUserDTO modifyUserDTO
    ) {
        UserResponse userResponse = modifyUserUserCase.modifyUser(
                UserEntity.builder()
                        .id(userId)
                        .userName(modifyUserDTO.getUsername())
                        .email(modifyUserDTO.getEmail())
                        .password(modifyUserDTO.getPassword())
                        .build()
        );
        return ResponseEntity.status(200).body(APIResponse.ok(userResponse));
    }

    // 유저 삭제
}






















