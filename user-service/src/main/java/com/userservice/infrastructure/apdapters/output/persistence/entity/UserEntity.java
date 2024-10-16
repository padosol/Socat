package com.userservice.infrastructure.apdapters.output.persistence.entity;

import jakarta.persistence.*;
import lombok.Builder;

import java.time.LocalDateTime;

@Entity
@Builder
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

    @Column(name = "status", length = 10)
    private String status;
}
