package com.room.roomservice.domain.room.domain

import java.time.LocalDateTime
import java.util.*

class Room(
    val userId: String,
    var roomId: String? = null,
    var roomName: String,
    var createAt: LocalDateTime? = null,
){

    companion object {
        fun create(userId: String, roomName: String): Room {
            return Room(
                userId = userId,
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