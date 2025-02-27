package socat.postservice.infrastructure.vo

import socat.postservice.infrastructure.web.dto.response.post.PostResponse
import java.time.LocalDateTime

data class CategoryResponse(
    val categoryId: String,
    val userId: String,
    val categoryName: String,
    val categoryDesc: String?,
    val createdAt: LocalDateTime,
    val topicId: String,
    val posts: List<PostResponse>
)
