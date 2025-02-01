package socat.postservice.global.exception

import lombok.extern.slf4j.Slf4j
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import socat.postservice.global.dto.APIResponse
import socat.postservice.global.dto.ErrorResponse

@Slf4j
@RestControllerAdvice
class PostExceptionAdvice {

    @ExceptionHandler(PostException::class)
    fun handlePostException(e: PostException): ResponseEntity<APIResponse<Any>> {
        return ResponseEntity.ok(APIResponse.fail<Any>(ErrorResponse(e.postExceptionCode.status.value(), e.postExceptionCode.message)))
    }

}