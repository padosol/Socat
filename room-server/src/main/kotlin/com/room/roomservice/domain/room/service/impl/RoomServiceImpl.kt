package com.room.roomservice.domain.room.service.impl

import com.room.roomservice.domain.room.client.ChatServiceClient
import com.room.roomservice.domain.room.client.UserServiceClient
import com.room.roomservice.domain.room.document.RoomDoc
import com.room.roomservice.domain.room.domain.Room
import com.room.roomservice.domain.room.domain.RoomClockHolder
import com.room.roomservice.domain.room.domain.RoomIdGenerator
import com.room.roomservice.domain.room.dto.response.RoomResponse
import com.room.roomservice.domain.room.repository.RoomRepository
import com.room.roomservice.domain.room.service.RoomService
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import java.lang.IllegalStateException

@Service
class RoomServiceImpl(
    private val redisTemplate: RedisTemplate<String, Any>,
    private val roomRepository: RoomRepository,
    private val userServiceClient: UserServiceClient,
    private val charServiceClient: ChatServiceClient
) : RoomService {

    override fun createRoom(room: Room): RoomResponse {

        val userId = room.userId
        val user = userServiceClient.getUser(userId)

        val roomClockHolder = RoomClockHolder()
        room.createRoom(roomClockHolder)

        val saveRoom: Room = roomRepository.save(RoomDoc.create(room))

        return RoomResponse(
            roomId =  saveRoom.roomId!!,
            roomName = saveRoom.roomName,
            createdAt = saveRoom.createAt!!
        )
    }

    override fun findRoom(roomId: String): RoomResponse {

        val findRoom: Room = roomRepository.findById(roomId)
            ?: throw IllegalStateException("존재하지 않는 방입니다.")

        val chats = charServiceClient.getChats(findRoom.roomId!!)
        findRoom.addChats(chats)

        return RoomResponse(
            roomId =  findRoom.roomId!!,
            roomName = findRoom.roomName,
            createdAt = findRoom.createAt!!
        )
    }

    override fun findAllRoom(): List<RoomResponse> {

        val findAllRoom = roomRepository.findAll()

        return findAllRoom.map {
            RoomResponse(
                roomId = it.roomId!!,
                roomName = it.roomName,
                createdAt = it.createAt!!
            )
        }.toList()
    }

}