package com.room.roomservice.global.exception

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionAdvice {

    @ExceptionHandler(CustomException::class)
    fun handleCustomException(e: CustomException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
                e.customExceptionCode.status.value(),
                e.customExceptionCode.message,
        )

        return ResponseEntity(errorResponse, e.customExceptionCode.status)
    }
}