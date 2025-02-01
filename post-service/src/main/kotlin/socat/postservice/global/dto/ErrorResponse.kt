package socat.postservice.global.dto

import java.time.LocalDateTime

class ErrorResponse(
        val timestamp: LocalDateTime,
        val status: Int,
        val error: String,
        val message: Map<String, String>?
) {
    constructor(status: Int, error: String) : this(LocalDateTime.now(), status, error, emptyMap())
    constructor(status: Int, error: String, message: Map<String, String>?) : this(LocalDateTime.now(), status, error, message)
}