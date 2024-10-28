package com.room.roomservice.chat.repository

import com.room.roomservice.domain.room.dto.request.ChatRoomDTO
import org.springframework.stereotype.Repository

@Repository
class ChatRoomRepository(
    private val chatRoomDTOMap: MutableMap<String, ChatRoomDTO> = HashMap()
) {

    fun findAllRoom(): List<ChatRoomDTO> {
        val rooms = chatRoomDTOMap.values.toList()
        return rooms.reversed()
    }

    fun findRoomById(roomId: String): ChatRoomDTO? {
        return chatRoomDTOMap.get(roomId)
    }

    fun createChatRoomDTO(name: String): ChatRoomDTO {
        val room = ChatRoomDTO.create(name)
        chatRoomDTOMap[room.roomId] = room

        return room
    }

}