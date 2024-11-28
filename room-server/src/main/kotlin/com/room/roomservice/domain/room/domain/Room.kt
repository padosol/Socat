package com.room.roomservice.domain.room.domain

import com.room.roomservice.domain.room.dto.response.RoomResponse
import java.time.LocalDateTime

class Room(
    val roomId: String,
    val userId: String,
    var roomName: String,
    var createdAt: LocalDateTime,
    var updatedAt: LocalDateTime? = null,
){

    companion object {
        fun create(userId: String, roomName: String, idGenerator: IdGenerator): Room {
            return Room(
                userId = userId,
                roomId = idGenerator.createId(),
                roomName = roomName,
                createdAt = LocalDateTime.now()
            )
        }

    }

    fun modifyRoom(room: Room) {
        roomName = room.roomName
        updatedAt = LocalDateTime.now()
    }

    fun toDto(): RoomResponse {
        return RoomResponse(
            roomId = this.roomId ,
            userId = userId ,
            roomName = roomName ,
            createdAt = createdAt
        )
    }

}