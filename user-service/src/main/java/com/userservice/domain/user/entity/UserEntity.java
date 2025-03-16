package com.userservice.domain.user.entity;

import com.userservice.domain.auth.entity.Auth;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Table(name = "users")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

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

    @Enumerated(EnumType.STRING)
    @Column(name = "state", length = 10)
    private UserState state;

    @ManyToMany
    @JoinTable(
            name = "user_auth",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "auth_name", referencedColumnName = "auth_name")}
    )
    private Set<Auth> authorities = new HashSet<>();


    // 유저 생성
    public void createUser(IdGenerator generator, Set<Auth> authorities) {
        this.id = generator.generate();
        this.createdAt = LocalDateTime.now();
        this.state = UserState.ACTIVE;
        this.authorities = authorities;
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
