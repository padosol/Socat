package com.userservice.domain.auth.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;

@Embeddable
public enum AuthType {
    USER, ADMIN
}
