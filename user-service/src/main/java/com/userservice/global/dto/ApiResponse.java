package com.userservice.global.dto;

import lombok.Getter;

@Getter
public class ApiResponse<T> {
    private boolean success;
    private T data;
    private ErrorResponse error;

    private ApiResponse(){}

    public ApiResponse(T data, boolean success, ErrorResponse error){
        this.data = data;
        this.success = success;
        this.error = error;
    }

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(data, true, null);
    }

    public static <T> ApiResponse<T> fail(ErrorResponse error) {
        return new ApiResponse<>(null, false, error);
    }


}
