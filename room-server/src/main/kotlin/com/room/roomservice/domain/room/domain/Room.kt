package com.room.roomservice.domain.room.domain

import com.room.roomservice.domain.room.vo.ChatResponse
import lombok.Builder
import java.time.LocalDateTime
import java.util.*

class Room(
    val roomId: String,
    val userId: String,
    var roomName: String,
    var createAt: LocalDateTime,
    var updatedAt: LocalDateTime? = null,
    var chats: MutableList<ChatResponse> = mutableListOf()
){

    companion object {
        fun create(userId: String, roomName: String, idGenerator: RoomIdGenerator): Room {
            return Room(
                userId = userId,
                roomId = idGenerator.createId(),
                roomName = roomName,
                createAt = LocalDateTime.now()
            )
        }
    }

    fun createRoom(clockHolder: ClockHolder) {
        createAt = LocalDateTime.now()
    }

    fun addChats(chats: MutableList<ChatResponse>) {
        this.chats = chats
    }

    fun modifyRoom(room: Room) {
        roomName = room.roomName
        updatedAt = LocalDateTime.now()
    }



}