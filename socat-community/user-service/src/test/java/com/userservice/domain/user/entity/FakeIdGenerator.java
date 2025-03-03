package com.userservice.domain.user.entity;

import java.util.UUID;

public class FakeIdGenerator implements IdGenerator{
    private String id;
    @Override
    public String generate() {
        this.id = UUID.randomUUID().toString();
        return id;
    }

    public String getId(){
        return this.id;
    }
}
