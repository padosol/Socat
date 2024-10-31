package com.chatservice.domain.dto

import java.util.*

class ChatRoomDTO(
    val roomId: String,
    val name: String,
) {

    companion object {
        fun create(name: String): ChatRoomDTO{
            return ChatRoomDTO(
                roomId = UUID.randomUUID().toString(),
                name = name
            )
        }
    }

}