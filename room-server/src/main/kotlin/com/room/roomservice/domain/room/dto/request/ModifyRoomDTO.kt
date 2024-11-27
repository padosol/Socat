package com.room.roomservice.domain.room.dto.request

import java.time.LocalDateTime

data class ModifyRoomDTO(
    val roomId: String,
    val roomName: String,
    val userId: String
)
