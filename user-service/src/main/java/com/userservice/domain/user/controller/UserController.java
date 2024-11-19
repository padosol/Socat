package com.userservice.domain.user.controller;


import com.userservice.domain.user.entity.User;
import com.userservice.domain.user.service.CreateUserUseCase;
import com.userservice.domain.user.controller.dto.request.CreateUserDTO;
import com.userservice.domain.user.controller.dto.response.UserResponse;
import com.userservice.domain.user.service.GetUserUseCase;
import com.userservice.global.utils.JwtProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User", description = "User API")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final GetUserUseCase getUserUseCase;
    private final CreateUserUseCase createUserUseCase;
    private final JwtProvider jwtProvider;

    @Operation(summary = "유저 회원가입", description = "유저 회원가입을 한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "회원가입 성공"),
            @ApiResponse(responseCode = "400", description = "올바르지 않은 파라미터")
    })
    @PostMapping("/users")
    public ResponseEntity<UserResponse> createUser(
            @RequestBody @Valid CreateUserDTO createUserDTO
    ) {
        UserResponse user = createUserUseCase.createUser(
                User.builder()
                    .userName(createUserDTO.getUsername())
                    .email(createUserDTO.getEmail())
                    .password(createUserDTO.getPassword())
                    .build()
        );

        return ResponseEntity.status(201).body(user);
    }
    
    // 유저 검색, 유저 정보 얻기
    @GetMapping("/users")
    public ResponseEntity<UserResponse> getUser(
            HttpServletRequest request
    ) {
        String userEmail = jwtProvider.getUserId(request);

        UserResponse userResponse = getUserUseCase.findUserByEmail(userEmail);

        return ResponseEntity.status(200).body(userResponse);
    }


    // 유저 수정

    // 유저 삭제
}


















