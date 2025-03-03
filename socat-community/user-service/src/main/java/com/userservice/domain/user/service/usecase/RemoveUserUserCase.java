package com.userservice.domain.user.service.usecase;


import com.userservice.domain.user.entity.UserEntity;

public interface RemoveUserUserCase {
    void removeUser(UserEntity user);
}
