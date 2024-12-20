package com.room.roomservice.domain.room.exception

import com.room.roomservice.global.exception.CustomException
import com.room.roomservice.global.exception.CustomExceptionCode
import org.apache.http.HttpStatus

class RoomNotFoundException(
        override val customExceptionCode: CustomExceptionCode
) : CustomException(customExceptionCode){
}