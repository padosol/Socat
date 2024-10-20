package com.userservice.global.dto;

import lombok.Getter;

@Getter
public class ErrorResult {
    private int code;
    private String message;

    public ErrorResult(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
