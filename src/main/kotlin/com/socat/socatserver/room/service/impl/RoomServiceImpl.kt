package com.socat.socatserver.room.service.impl

import com.socat.socatserver.room.repository.RoomRepository
import com.socat.socatserver.room.domain.Room
import com.socat.socatserver.room.dto.request.ChatRoomDTO
import com.socat.socatserver.room.dto.response.RoomResponse
import com.socat.socatserver.room.service.RoomService
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
class RoomServiceImpl(
    private val redisTemplate: RedisTemplate<String, Any>,
) : RoomService{
    fun createRoom(name: String): ChatRoomDTO {

        val rooms = redisTemplate.opsForHash<String, Any>()

        val createdRoom = ChatRoomDTO.create(name)

        rooms.put("CHAT_ROOM", createdRoom.roomId, createdRoom)

        return createdRoom
    }

    override fun createRoom(room: Room): RoomResponse {

        return RoomResponse(
            roomId =  UUID.randomUUID().toString(),
            roomName = "test",
            createdAt = LocalDateTime.now()
        )
    }

}