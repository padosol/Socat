package com.socat.socatserver.chat.domain

import java.time.LocalDateTime
import java.util.*

class Room(
    roomId: String,
    roomName: String,
    createAt: LocalDateTime,
) {

    companion object {
        fun create(roomName: String): Room {
            return Room(
                roomId = UUID.randomUUID().toString(),
                roomName = roomName,
                createAt = LocalDateTime.now()
            )
        }

    }

}