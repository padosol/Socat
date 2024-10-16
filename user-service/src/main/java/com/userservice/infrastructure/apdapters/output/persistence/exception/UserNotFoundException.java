package com.userservice.infrastructure.apdapters.output.persistence.exception;

import com.userservice.global.exception.CustomException;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends CustomException {

    public UserNotFoundException(HttpStatus status, String message) {
        super(status, message);
    }
}
