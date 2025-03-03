package com.userservice.domain.user.utils;

import com.userservice.domain.user.entity.IdGenerator;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserIdGenerator implements IdGenerator {
    @Override
    public String generate() {
        return UUID.randomUUID().toString();
    }
}
