package com.room.roomservice.domain.room.service.impl

import com.room.roomservice.domain.room.client.ChatServiceClient
import com.room.roomservice.domain.room.client.UserServiceClient
import com.room.roomservice.domain.room.document.RoomDoc
import com.room.roomservice.domain.room.domain.Room
import com.room.roomservice.domain.room.domain.RoomClockHolder
import com.room.roomservice.domain.room.domain.RoomIdGenerator
import com.room.roomservice.domain.room.dto.response.RoomResponse
import com.room.roomservice.domain.room.exception.RoomNoRightRemoveException
import com.room.roomservice.domain.room.exception.RoomNotFoundException
import com.room.roomservice.domain.room.repository.RoomRepository
import com.room.roomservice.domain.room.service.RoomService
import com.room.roomservice.domain.room.service.usecase.ModifyRoomUseCase
import com.room.roomservice.domain.room.service.usecase.RemoveRoomUseCase
import com.room.roomservice.global.exception.CustomExceptionCode
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import java.lang.IllegalStateException

@Service
class RoomServiceImpl(
    private val redisTemplate: RedisTemplate<String, Any>,
    private val roomRepository: RoomRepository,
    private val userServiceClient: UserServiceClient,
) : RoomService, ModifyRoomUseCase, RemoveRoomUseCase {

    override fun createRoom(room: Room): RoomResponse {
        val userId = room.userId
        val user = userServiceClient.getUser(userId)

        val roomClockHolder = RoomClockHolder()
        room.createRoom(roomClockHolder)

        val saveRoom: Room = roomRepository.save(RoomDoc.create(room))

        return RoomResponse(
                roomId =  saveRoom.roomId!!,
                userId = user.id,
                roomName = saveRoom.roomName,
                createdAt = saveRoom.createAt!!
        )
    }

    override fun findRoom(roomId: String): RoomResponse {

        val findRoom: Room = roomRepository.findById(roomId)
            ?: throw RoomNotFoundException(CustomExceptionCode.ROOM_NOT_FOUND)

        return RoomResponse(
            roomId =  findRoom.roomId!!,
            userId = findRoom.userId,
            roomName = findRoom.roomName,
            createdAt = findRoom.createAt!!
        )
    }

    override fun findAllRoom(): List<RoomResponse> {

        val findAllRoom = roomRepository.findAll()

        return findAllRoom.map {
            RoomResponse(
                roomId = it.roomId!!,
                userId = it.roomId,
                roomName = it.roomName,
                createdAt = it.createAt!!
            )
        }.toList()
    }

    override fun modify(room: Room): RoomResponse {
        val roomId = room.roomId

        val findRoom: Room = roomRepository.findById(roomId) ?: throw RuntimeException()
        findRoom.modifyRoom(room)

        val saveRoom = roomRepository.save(
                RoomDoc(
                        roomId = findRoom.roomId,
                        userId = findRoom.userId,
                        roomName = findRoom.roomName,
                        createdAt = findRoom.createAt,
                        updatedAt = findRoom.updatedAt
                )
        )

        return RoomResponse(
                roomId = saveRoom.roomId,
                userId = saveRoom.userId,
                roomName = saveRoom.roomName,
                createdAt = saveRoom.createAt
        )
    }

    override fun remove(roomId: String, userId: String) {
        val room = roomRepository.findRoomByRoomIdAndUserId(roomId, userId)

        if(room.userId != userId) {
            throw RoomNoRightRemoveException(CustomExceptionCode.ROOM_NO_RIGHT_DELETE)
        }

        roomRepository.delete(room.roomId)
    }

}