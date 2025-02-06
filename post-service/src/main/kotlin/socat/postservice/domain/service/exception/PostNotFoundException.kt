package socat.postservice.domain.service.exception

import socat.postservice.global.exception.PostException
import socat.postservice.global.exception.PostExceptionCode

class PostNotFoundException(
    override val postExceptionCode: PostExceptionCode
) : PostException(postExceptionCode)