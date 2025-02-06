package com.room.roomservice.domain.room.dto.response

import com.room.roomservice.domain.room.vo.PostResponse
import java.time.LocalDateTime

data class RoomResponse(
    val roomId: String,
    val userId: String,
    val roomName: String,
    val roomDesc: String?,
    val createdAt: LocalDateTime,
    val roomType: String,
    val posts: List<PostResponse> = listOf()
)