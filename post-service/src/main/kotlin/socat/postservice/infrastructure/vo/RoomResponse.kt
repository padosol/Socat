package socat.postservice.infrastructure.vo

import socat.postservice.infrastructure.web.dto.response.PostResponse
import java.time.LocalDateTime

data class RoomResponse(
    val roomId: String,
    val userId: String,
    val roomName: String,
    val roomDesc: String?,
    val createdAt: LocalDateTime,
    val roomType: String,
    val posts: List<PostResponse>
)
