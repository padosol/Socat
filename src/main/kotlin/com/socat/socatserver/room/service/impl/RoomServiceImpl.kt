package com.socat.socatserver.room.service.impl

import com.socat.socatserver.room.document.RoomDoc
import com.socat.socatserver.room.domain.Room
import com.socat.socatserver.room.domain.RoomIdGenerator
import com.socat.socatserver.room.dto.response.RoomResponse
import com.socat.socatserver.room.repository.RoomMongoRepository
import com.socat.socatserver.room.repository.RoomRepository
import com.socat.socatserver.room.service.RoomService
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import java.lang.IllegalStateException
import java.util.*

@Service
class RoomServiceImpl(
    private val redisTemplate: RedisTemplate<String, Any>,
    private val roomRepository: RoomRepository,
) : RoomService{

    override fun createRoom(room: Room): RoomResponse {

        val roomIdGenerator = RoomIdGenerator()
        room.createRoom(roomIdGenerator)

        val saveRoom: Room = roomRepository.save(RoomDoc.create(room))

        return RoomResponse(
            roomId =  saveRoom.roomId!!,
            roomName = saveRoom.roomName,
            createdAt = saveRoom.createAt!!
        )
    }

    override fun findRoom(roomId: String): RoomResponse {

        val findRoom = roomRepository.findById(roomId)
            ?: throw IllegalStateException("존재하지 않는 방입니다.")

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