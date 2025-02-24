package socat.postservice.infrastructure.web.dto.response.post

import java.time.LocalDateTime

data class PostResponse(
        val postId: String,
        val userId: String,
        val title: String,
        val content: String,
        val createdAt: LocalDateTime,
        val updatedAt: LocalDateTime?,
        val username: String? = null,
)
