package com.userservice.domain.user.service.usecase;

import com.userservice.domain.user.controller.dto.response.UserResponse;
import com.userservice.domain.user.entity.User;

public interface ModifyUserUserCase {
    UserResponse modifyUser(User user);
}
