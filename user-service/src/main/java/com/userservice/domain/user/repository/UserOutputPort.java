package com.userservice.domain.user.repository;

import com.userservice.domain.user.User;
import com.userservice.domain.user.entity.UserEntity;

public interface UserOutputPort {
    User save(UserEntity user);

    User findUserById(String id);

    User removeUser(UserEntity user);

}
