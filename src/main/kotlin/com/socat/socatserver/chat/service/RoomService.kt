package com.socat.socatserver.chat.service

import com.socat.socatserver.chat.domain.ChatRoomDTO
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service

@Service
class RoomService(
    private val redisTemplate: RedisTemplate<String, Any>
) {
    fun createRoom(name: String): ChatRoomDTO {

        val rooms = redisTemplate.opsForHash<String, Any>()

        val createdRoom = ChatRoomDTO.create(name)

        rooms.put("CHAT_ROOM", createdRoom.roomId, createdRoom)

        return createdRoom
    }

}