package com.room.roomservice.domain.room.dto.request

data class CreateRoomDTO(
    val userId: String,
    val roomName: String,
)