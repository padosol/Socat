package com.userservice.domain.user.repository;

import com.userservice.domain.user.User;
import com.userservice.domain.user.entity.UserEntity;
import com.userservice.domain.user.exception.UserNotFoundException;
import com.userservice.domain.user.repository.mapper.UserEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserPersistenceAdapter implements UserOutputPort {

    private final UserJpaRepository userJpaRepository;

    @Override
    public User save(UserEntity user) {

        UserEntity userEntity = userJpaRepository.save(user);

        return UserEntityMapper.toUser(userEntity);
    }

    @Override
    public User findUserById(String id) {

        UserEntity userEntity = userJpaRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException(HttpStatus.NOT_FOUND, "유저가 존재하지 않습니다.")
        );

        return UserEntityMapper.toUser(userEntity);
    }

    @Override
    public User removeUser(UserEntity user) {

        UserEntity userEntity = userJpaRepository.save(user);

        return UserEntityMapper.toUser(userEntity);
    }

}
