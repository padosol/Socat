package com.userservice.domain.user.service;

import com.userservice.domain.user.entity.IdGenerator;
import com.userservice.domain.user.entity.User;
import com.userservice.domain.user.exception.UserAlreadyExistException;
import com.userservice.domain.user.repository.UserJpaRepository;
import com.userservice.domain.user.controller.dto.response.UserResponse;
import com.userservice.domain.user.service.usecase.CreateUserUseCase;
import com.userservice.domain.user.service.usecase.GetUserUseCase;
import com.userservice.domain.user.service.usecase.ModifyUserUserCase;
import com.userservice.domain.user.service.usecase.RemoveUserUserCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements
        CreateUserUseCase,
        ModifyUserUserCase,
        RemoveUserUserCase,
        UserDetailsService,
        GetUserUseCase
{

    private final UserJpaRepository userJpaRepository;
    private final IdGenerator idGenerator;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse createUser(User user) {

        // 해당 이메일로 가입되어 있는 유저 체크
        Optional<User> findUser = userJpaRepository.findUserByEmail(user.getEmail());
        if(findUser.isPresent()) {
            throw new UserAlreadyExistException(HttpStatus.BAD_REQUEST, "이미 가입되어 있는 유저 입니다.");
        }

        user.createUser(idGenerator);
        user.encoderPassword(passwordEncoder);

        User saveUser = userJpaRepository.save(user);

        return UserResponse.builder()
                .username(saveUser.getUserName())
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
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userJpaRepository.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("유저정보가 없습니다."));

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("USER_ROLE"));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getId())
                .password(user.getPassword())
                .authorities(grantedAuthorities)
                .build();
    }

    @Override
    public UserResponse findUserByEmail(String email) {
        User user = userJpaRepository.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("유저정보가 없습니다."));

        return UserResponse.builder()
                .username(user.getUserName())
                .email(user.getEmail())
                .id(user.getId())
                .build();
    }

    @Override
    public UserResponse findUserById(String id) {

        User user = userJpaRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("유저정보가 없습니다."));

        return UserResponse.builder()
                .username(user.getUserName())
                .email(user.getEmail())
                .id(user.getId())
                .build();
    }
}