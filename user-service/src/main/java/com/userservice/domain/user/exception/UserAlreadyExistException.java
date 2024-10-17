package com.userservice.domain.user.exception;

import com.userservice.global.exception.CustomException;
import org.springframework.http.HttpStatus;

public class UserAlreadyExistException extends CustomException {
    public UserAlreadyExistException(HttpStatus status, String message) {
        super(status, message);
    }
}
