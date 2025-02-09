package socat.postservice.global.exception

import org.springframework.http.HttpStatus

enum class PostExceptionCode(
        val status: HttpStatus,
        val message: String,
) {
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 게시글 입니다."),
    ROOM_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 방 입니다."),
    FILE_EXTENSION_NOT_EXIST(HttpStatus.BAD_REQUEST, "파일 확장자가 존재하지 않습니다."),
    FILE_EXTENSION_NOT_SUPPORT(HttpStatus.BAD_REQUEST, "지원하지 않는 파일 확장자 입니다."),
    WRONG_FORMAT_FILE_NAME(HttpStatus.BAD_REQUEST, "올바르지 않는 파일명 입니다.")

}