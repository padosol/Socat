package com.userservice.domain.user.service;

import com.userservice.domain.user.entity.IdGenerator;
import com.userservice.domain.user.entity.User;
import com.userservice.domain.user.exception.UserAlreadyExistException;
import com.userservice.domain.user.repository.UserJpaRepository;
import com.userservice.domain.user.controller.dto.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements CreateUserUseCase, ModifyUserUserCase, RemoveUserUserCase, UserDetailsService {

    private final UserJpaRepository userJpaRepository;
    private final IdGenerator idGenerator;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse createUser(User user) {

        // 해당 이메일로 가입되어 있는 유저 체크
        userJpaRepository.findUserByEmail(user.getEmail()).orElseThrow(
                () -> new UserAlreadyExistException(HttpStatus.BAD_REQUEST, "이미 가입되어 있는 유저 입니다.")
        );

        user.createUser(idGenerator);
        user.encoderPassword(passwordEncoder);

        User saveUser = userJpaRepository.save(user);

        return UserResponse.builder()
                .userName(saveUser.getUserName())
                .id(saveUser.getId())
                .email(saveUser.getEmail())
                .build();
    }

    @Override
    public UserResponse modifyUser(User user) {
        // 데이터베이스에서 유저를 찾아서 있으면 수정 없으면 error



        return null;
    }

    @Override
    public void removeUser(User user) {

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}