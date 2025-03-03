package com.userservice.domain.auth.exception;

import com.userservice.global.exception.CustomException;
import org.springframework.http.HttpStatus;

public class RefreshTokenNotMatchException extends CustomException {
    public RefreshTokenNotMatchException(HttpStatus status, String message) {
        super(status, message);
    }
}
