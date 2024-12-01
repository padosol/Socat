package com.room.roomservice.domain.room.repository.impl

import com.room.roomservice.domain.room.document.RoomDoc
import com.room.roomservice.domain.room.domain.Room
import com.room.roomservice.domain.room.exception.RoomNotFoundException
import com.room.roomservice.domain.room.repository.RoomMongoRepository
import com.room.roomservice.domain.room.repository.RoomRepository
import com.room.roomservice.global.exception.CustomExceptionCode
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class RoomRepositoryImpl(
    private val roomMongoRepository: RoomMongoRepository
): RoomRepository {
    override fun save(roomDoc: RoomDoc): Room {

        val save = roomMongoRepository.save(roomDoc)

        return Room(
            userId = save.userId,
            roomId = save.roomId,
            roomName = save.roomName,
            createdAt = save.createdAt
        )
    }

    override fun findById(roomId: String): Room? {
        return roomMongoRepository.findByIdOrNull(roomId)
            ?.let {
                Room(
                    userId = it.userId,
                    roomId = it.roomId,
                    roomName = it.roomName,
                    createdAt = it.createdAt
                )
            }
    }

    override fun findAll(): List<Room> {
        return  roomMongoRepository.findAll().map {
            Room(
                userId = it.userId,
                roomId = it.roomId,
                roomName = it.roomName,
                createdAt = it.createdAt
            )
        }
    }

    override fun delete(roomId: String) {
        roomMongoRepository.deleteById(roomId)
    }

    override fun findRoomByRoomIdAndUserId(roomId: String, userId: String): Room {
        val roomDoc = roomMongoRepository.findByIdOrNull(roomId)
                ?: throw RoomNotFoundException(CustomExceptionCode.ROOM_NOT_FOUND)

        return Room(
                roomId = roomDoc.roomId,
                userId = roomDoc.userId,
                roomName = roomDoc.roomName,
                createdAt = roomDoc.createdAt
        )
    }


}