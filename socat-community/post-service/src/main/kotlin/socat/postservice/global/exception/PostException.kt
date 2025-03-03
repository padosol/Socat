package socat.postservice.global.exception

open class PostException(
    open val postExceptionCode: PostExceptionCode
): RuntimeException()