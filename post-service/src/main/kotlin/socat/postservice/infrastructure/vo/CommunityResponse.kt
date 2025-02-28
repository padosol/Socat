package socat.postservice.infrastructure.vo

import socat.postservice.infrastructure.web.dto.response.post.PostResponse
import java.time.LocalDateTime

data class CommunityResponse(
    val communityId: String,
    val userId: String,
    val communityName: String,
    val communityDesc: String?,
    val createdAt: LocalDateTime,
    val topicId: String,
    val topicName: String,
    val posts: List<PostResponse> = listOf()
)
