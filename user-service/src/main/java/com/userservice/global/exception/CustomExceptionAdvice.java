package com.userservice.global.exception;

import com.userservice.global.dto.ErrorResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class CustomExceptionAdvice{

    @ExceptionHandler
    public ResponseEntity<ErrorResult> methodArgumentNotValidException(
            MethodArgumentNotValidException e
    ) {
        return getValidationErrorBody(e);
    }

    private ResponseEntity<ErrorResult> getValidationErrorBody(BindException exception) {
        BindingResult bindingResult = exception.getBindingResult();

        StringBuilder builder = new StringBuilder();
        bindingResult.getFieldErrors()
                .forEach(fieldError -> {
                    builder.append(fieldError.getDefaultMessage());
                    builder.append(" 입력된 값: [");
                    builder.append(fieldError.getRejectedValue());
                    builder.append("]");
                });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResult(HttpStatus.BAD_REQUEST.value(), builder.toString()));
    }



}
