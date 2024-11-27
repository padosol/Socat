package com.room.roomservice.global.exception

import org.springframework.http.HttpStatus


enum class CustomExceptionCode(
        val status: HttpStatus,
        val message: String
) {
    ROOM_NOT_FOUND(HttpStatus.NOT_FOUND, "방이 존재하지 않습니다."),
    ROOM_NO_RIGHT_DELETE(HttpStatus.BAD_REQUEST, "삭제 권한이 없습니다.")
}