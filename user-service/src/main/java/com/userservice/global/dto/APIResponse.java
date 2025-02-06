package com.userservice.global.dto;

import lombok.Getter;

@Getter
public class APIResponse<T> {
    private boolean success;
    private T data;
    private ErrorResponse error;

    private APIResponse(){}

    public APIResponse(T data, boolean success, ErrorResponse error){
        this.data = data;
        this.success = success;
        this.error = error;
    }

    public static <T> APIResponse<T> ok(T data) {
        return new APIResponse<>(data, true, null);
    }

    public static <T> APIResponse<T> fail(ErrorResponse error) {
        return new APIResponse<>(null, false, error);
    }


}
