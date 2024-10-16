package com.userservice.application.ports.output;

import com.userservice.domain.user.User;
import com.userservice.infrastructure.apdapters.output.persistence.entity.UserEntity;

public interface UserOutputPort {
    User save(UserEntity user);

    User findUserById(String id);

    User removeUser(UserEntity user);

}
