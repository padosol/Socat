package com.socat.socatserver.room.domain

import java.time.LocalDateTime
import java.util.*

class Room(
    var roomId: String? = null,
    var roomName: String,
    var createAt: LocalDateTime? = null,
){

    companion object {
        fun create(roomName: String): Room {
            return Room(
                roomId = UUID.randomUUID().toString(),
                roomName = roomName,
                createAt = LocalDateTime.now()
            )
        }

    }

    fun createRoom(idGenerator: IdGenerator) {
        roomId = idGenerator.createId()
        createAt = LocalDateTime.now()
    }

}