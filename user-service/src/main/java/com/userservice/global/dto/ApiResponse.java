package com.userservice.global.dto;

import lombok.Getter;

@Getter
public class ApiResponse<T> {
    private boolean success;
    private T data;
    private ErrorResponse error;
}
