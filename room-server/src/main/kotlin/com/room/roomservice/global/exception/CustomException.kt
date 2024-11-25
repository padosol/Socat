package com.room.roomservice.global.exception

import org.apache.http.HttpStatus

open class CustomException(
        open val customExceptionCode: CustomExceptionCode
) : RuntimeException()