package com.community.communityservice.global.exception

import org.springframework.http.HttpStatus


enum class CustomExceptionCode(
        val status: HttpStatus,
        val message: String
) {
    ROOM_NOT_FOUND(HttpStatus.NOT_FOUND, "방이 존재하지 않습니다."),
    ROOM_NO_RIGHT_DELETE(HttpStatus.BAD_REQUEST, "삭제 권한이 없습니다."),
    ROOM_NO_RIGHT_MODIFY(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다."),
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "존재하지 않는 유저 아이디 입니다."),

    TOPIC_ID_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "이미 등록된 토픽 아이디입니다."),
    TOPIC_ID_NOT_EXISTS(HttpStatus.BAD_REQUEST, "존재하지 않는 토픽 아이디입니다.")
}