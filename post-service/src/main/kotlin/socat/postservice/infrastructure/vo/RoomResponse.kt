package socat.postservice.infrastructure.vo

import java.time.LocalDateTime

data class RoomResponse(
    val roomId: String,
    val userId: String,
    val roomName: String,
    val roomDesc: String,
    val createdAt: LocalDateTime,
    val roomType: String,
)
