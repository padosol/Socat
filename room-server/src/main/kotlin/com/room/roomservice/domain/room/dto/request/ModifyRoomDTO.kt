package com.room.roomservice.domain.room.dto.request

data class ModifyRoomDTO(
    val roomId: String,
    val roomName: String,
    val userId: String
)
