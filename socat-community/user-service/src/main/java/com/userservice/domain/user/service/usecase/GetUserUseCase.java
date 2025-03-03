package com.userservice.domain.user.service.usecase;

import com.userservice.domain.user.controller.dto.response.UserResponse;

import java.util.List;
import java.util.Map;

public interface GetUserUseCase {
    UserResponse findUserByEmail(String email);

    UserResponse findUserById(String id);

    Map<String, UserResponse> findUserInfoByIdMulti(List<String> userIds);
}
