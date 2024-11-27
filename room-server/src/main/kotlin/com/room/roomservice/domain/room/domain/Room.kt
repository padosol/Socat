package com.room.roomservice.domain.room.domain

import java.time.LocalDateTime

class Room(
    val roomId: String,
    val userId: String,
    var roomName: String,
    var createAt: LocalDateTime? = null,
    var updatedAt: LocalDateTime? = null,
){

    companion object {
        fun create(userId: String, roomName: String, idGenerator: IdGenerator): Room {
            return Room(
                userId = userId,
                roomId = idGenerator.createId(),
                roomName = roomName
            )
        }

    }

    fun createRoom(clockHolder: ClockHolder) {
        createAt = LocalDateTime.now()
    }

    fun modifyRoom(room: Room) {
        roomName = room.roomName
        updatedAt = LocalDateTime.now()
    }

}