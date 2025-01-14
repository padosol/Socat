package com.userservice.global.dto;

import lombok.Getter;

@Getter
public class ErrorResponse {
    private int status;
    private String errorMessage;

    private ErrorResponse(){}

    public ErrorResponse(int status, String errorMessage) {
        this.status = status;
        this.errorMessage = errorMessage;
    }
}
