package socat.postservice.infrastructure.web.dto.response

import java.time.LocalDateTime

data class PostResponse(
        private val postId: String,
        private val userId: String,
        private val title: String,
        private val content: String,
        private val createdAt: LocalDateTime,
        private val updatedAt: LocalDateTime?
)
