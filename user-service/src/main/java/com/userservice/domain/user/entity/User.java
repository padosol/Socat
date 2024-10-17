package com.userservice.domain.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @Column(name = "user_id", length = 36)
    private String id;

    @Column(name = "user_name", length = 10)
    private String userName;

    @Column(name = "email", unique = true, length = 20)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "status", length = 10)
    private String status;

    @Enumerated(EnumType.STRING)
    @Column(name = "state", length = 10)
    private UserState state;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Set<UserAuth> authorities;


    // 유저 생성
    public void createUser(IdGenerator generator) {
        this.id = generator.generate();
        this.createdAt = LocalDateTime.now();
    }

    public void encoderPassword(PasswordEncoder encoder) {
        // 비밀번호 암호화
        this.password = encoder.encode(this.password);
    }

    // 유저 삭제 soft delete
    public void deleteUser() {
        this.state = UserState.DELETE;
    }
}
