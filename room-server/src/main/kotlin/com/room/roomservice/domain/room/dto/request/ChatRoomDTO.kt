package com.room.roomservice.domain.room.dto.request

import org.springframework.web.socket.WebSocketSession
import java.util.*
import kotlin.collections.HashSet

data class ChatRoomDTO(
    var roomId: String,
    var name: String?,
    var clients: MutableSet<WebSocketSession> = HashSet()
) {

    companion object {
        fun create(name: String): ChatRoomDTO {
            return ChatRoomDTO(
                roomId = UUID.randomUUID().toString(),
                name = name
            )
        }
    }
}