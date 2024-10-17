package com.userservice.domain.user.service;

import com.userservice.domain.user.User;
import com.userservice.domain.user.controller.dto.response.UserResponse;

public interface ModifyUserUserCase {
    UserResponse modifyUser(User user);
}
