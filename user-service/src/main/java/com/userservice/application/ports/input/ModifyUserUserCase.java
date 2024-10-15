package com.userservice.application.ports.input;

import com.userservice.domain.user.User;
import com.userservice.infrastructure.apdapters.input.rest.dto.response.UserResponse;

public interface ModifyUserUserCase {
    UserResponse modifyUser(User user);
}
