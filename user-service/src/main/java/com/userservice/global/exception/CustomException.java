package com.userservice.global.exception;

import com.userservice.global.dto.ErrorResult;
import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException{

    private HttpStatus status;
    private ErrorResult errorResult;

    protected CustomException(HttpStatus status, String message) {
        this.status = status;
    }
}
