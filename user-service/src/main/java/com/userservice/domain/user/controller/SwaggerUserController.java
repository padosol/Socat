package com.userservice.domain.user.controller;

import com.userservice.domain.user.controller.dto.request.CreateUserDTO;
import com.userservice.domain.user.controller.dto.response.UserResponse;
import com.userservice.global.dto.APIResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface SwaggerUserController {

    @Operation(summary = "유저 아이디로 유저 조회", description = "유저 아이디로 유저를 조회한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "성공"),
            @ApiResponse(responseCode = "400", description = "올바르지 않은 파라미터")
    })
    ResponseEntity<APIResponse<UserResponse>> getUserByUserId(String userId);


    @Operation(summary = "JWT 로 유저 조회", description = "JWT 로 유저 정보를 조회한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "성공"),
            @ApiResponse(responseCode = "400", description = "올바르지 않은 파라미터")
    })
    ResponseEntity<UserResponse> getUser(HttpServletRequest request);


    @Operation(summary = "유저 회원가입", description = "유저 회원가입을 한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "성공"),
            @ApiResponse(responseCode = "400", description = "올바르지 않은 파라미터")
    })
    ResponseEntity<UserResponse> createUser(
            CreateUserDTO createUserDTO
    );
}
