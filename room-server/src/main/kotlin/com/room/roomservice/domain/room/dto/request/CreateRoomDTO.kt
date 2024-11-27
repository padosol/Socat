package com.room.roomservice.domain.room.dto.request

import org.springframework.util.StringUtils

data class CreateRoomDTO(
    val roomName: String,
) {
    init {
        require(StringUtils.hasText(roomName))
    }
}