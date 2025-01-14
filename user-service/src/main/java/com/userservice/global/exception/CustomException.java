package com.userservice.global.exception;

import com.userservice.global.dto.ErrorResult;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends RuntimeException{

    private HttpStatus status;
    private ErrorResult errorResult;

    protected CustomException(HttpStatus status, String message) {
        this.status = status;
        this.errorResult = new ErrorResult(status.value(), message);
    }
}
