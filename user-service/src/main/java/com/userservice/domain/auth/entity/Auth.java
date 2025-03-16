package com.userservice.domain.auth.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "auth")
public class Auth {

    @Id
    @Column(name = "auth_name", length = 10)
    private String id;

    public Auth(){};

    public Auth(String id) {
        this.id = id;
    }


}
