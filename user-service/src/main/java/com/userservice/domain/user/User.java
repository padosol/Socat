package com.userservice.domain.user;

import lombok.Builder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Builder
public class User {

    private String id;
    private String userName;
    private String email;
    private LocalDateTime createdAt;
    private String password;

    private UserState userState;


    // 유저 생성
    public void createUser(IdGenerator generator) {
        this.id = generator.generate();
        this.createdAt = LocalDateTime.now();
    }

    public void encoderPassword(PasswordEncoder encoder) {
    // 비밀번호 암호화
        this.password = encoder.encode(this.password);
    }

    // 유저 수정


    // 유저 삭제 soft delete
    public void deleteUser() {
        this.userState = UserState.DELETE;
    }


}