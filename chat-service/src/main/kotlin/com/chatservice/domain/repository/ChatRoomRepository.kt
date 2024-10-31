package com.chatservice.domain.repository

import com.chatservice.domain.dto.ChatRoomDTO
import org.springframework.stereotype.Repository

@Repository
class ChatRoomRepository {

    private val chatRoomMap: MutableMap<String, ChatRoomDTO> = mutableMapOf()


    fun findAllRoom(): MutableList<ChatRoomDTO> {
        return chatRoomMap.values.toMutableList().asReversed()
    }

    fun findRoomById(roomId: String):ChatRoomDTO? {
        return chatRoomMap[roomId]
    }

    fun createChatRoom(name: String): ChatRoomDTO {
        val room = ChatRoomDTO.create(name)
        chatRoomMap[room.roomId] = room
        return room
    }

}