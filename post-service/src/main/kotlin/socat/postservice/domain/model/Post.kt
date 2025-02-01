package socat.postservice.domain.model

import socat.postservice.infrastructure.web.dto.response.PostResponse
import java.time.LocalDateTime

class Post(
    val roomId: String,
    val postId: String,
    val title: String,
    val postDesc: String,
    val content: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val userId: String,
) {


    fun toDTO(): PostResponse {
        return PostResponse(
            postId = postId,
            userId = userId,
            title = title,
            content = content,
            createdAt = createdAt,
            updatedAt = updatedAt,
        )
    }
}