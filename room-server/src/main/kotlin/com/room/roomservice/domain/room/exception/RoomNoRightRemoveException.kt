package com.room.roomservice.domain.room.exception

import com.room.roomservice.global.exception.CustomException
import com.room.roomservice.global.exception.CustomExceptionCode

class RoomNoRightRemoveException(
        override val customExceptionCode: CustomExceptionCode
) : CustomException(customExceptionCode){
}