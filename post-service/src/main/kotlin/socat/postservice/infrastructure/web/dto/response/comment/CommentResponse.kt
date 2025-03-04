package socat.postservice.infrastructure.web.dto.response.comment

import java.time.LocalDateTime

data class CommentResponse(
    val commentId: String,
    val comment: String,
    val userId: String,
    val username: String? = null,
    val status: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime? = null
)
