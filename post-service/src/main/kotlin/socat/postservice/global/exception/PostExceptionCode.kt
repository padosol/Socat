package socat.postservice.global.exception

import org.springframework.http.HttpStatus

enum class PostExceptionCode(
        val status: HttpStatus,
        val message: String,
) {
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 게시글 입니다."),
    ROOM_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 방 입니다.")
}