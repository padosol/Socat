package com.userservice.infrastructure.apdapters.output.persistence.mapper;

import com.userservice.domain.user.User;
import com.userservice.infrastructure.apdapters.output.persistence.entity.UserEntity;

public class UserEntityMapper {

    public static User toUser(UserEntity userEntity) {
        return User.builder().build();
    }

    public static UserEntity toEntity(User user) {
        return UserEntity.builder().build();
    }

}
