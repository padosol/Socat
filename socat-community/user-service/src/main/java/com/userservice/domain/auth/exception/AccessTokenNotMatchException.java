package com.userservice.domain.auth.exception;

import com.userservice.global.exception.CustomException;
import org.springframework.http.HttpStatus;

public class AccessTokenNotMatchException extends CustomException {
    public AccessTokenNotMatchException(HttpStatus status, String message) {
        super(status, message);
    }
}
