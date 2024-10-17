package com.userservice.domain.user.repository.mapper;

import com.userservice.domain.user.User;
import com.userservice.domain.user.entity.UserEntity;

public class UserEntityMapper {

    public static User toUser(UserEntity userEntity) {
        return User.builder().build();
    }

    public static UserEntity toEntity(User user) {
        return UserEntity.builder().build();
    }

}
