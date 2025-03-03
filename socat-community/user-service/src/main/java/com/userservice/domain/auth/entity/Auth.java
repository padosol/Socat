package com.userservice.domain.auth.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "auth")
public class Auth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 10)
    @Embedded
    private AuthType authType;

}
