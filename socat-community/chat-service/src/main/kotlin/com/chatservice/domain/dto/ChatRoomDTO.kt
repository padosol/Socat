package com.chatservice.domain.dto

import java.io.Serializable
import java.util.*

data class ChatRoomDTO(
    val roomId: String,
    val name: String,
): Serializable {

    companion object {
        private const val serialVersionUID = 6494678977089006639L

        fun create(name: String): ChatRoomDTO{
            return ChatRoomDTO(
                roomId = UUID.randomUUID().toString(),
                name = name
            )
        }
    }

}