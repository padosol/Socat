package com.room.roomservice.domain.room.repository.impl

import com.room.roomservice.domain.room.domain.Room
import com.room.roomservice.domain.room.exception.RoomNotFoundException
import com.room.roomservice.domain.room.repository.RoomJpaRepository
import com.room.roomservice.domain.room.repository.RoomRepository
import com.room.roomservice.global.exception.CustomExceptionCode
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class RoomRepositoryImpl(
    private val roomJpaRepository: RoomJpaRepository
): RoomRepository {
    override fun save(room: Room): Room {
        val save = roomJpaRepository.save(room.toEntity())

        return Room(
                userId = save.userId,
                roomId = save.roomId,
                roomName = save.roomName,
                roomDesc = save.roomDesc,
                createdAt = save.createdAt,
                roomType = save.roomType,
        )
    }

    override fun findById(roomId: String): Room? {
        return roomJpaRepository.findByIdOrNull(roomId)
            ?.let {
                Room(
                    userId = it.userId,
                    roomId = it.roomId,
                    roomName = it.roomName,
                    roomDesc = it.roomDesc,
                    createdAt = it.createdAt,
                    roomType = it.roomType,
                )
            }
    }

    override fun findAll(): List<Room> {
        return  roomJpaRepository.findAll().map {
            Room(
                userId = it.userId,
                roomId = it.roomId,
                roomName = it.roomName,
                roomDesc = it.roomDesc,
                createdAt = it.createdAt,
                roomType = it.roomType,
            )
        }
    }

    override fun delete(roomId: String) {
        roomJpaRepository.deleteById(roomId)
    }

    override fun findRoomByRoomIdAndUserId(roomId: String, userId: String): Room {
        val roomEntity = roomJpaRepository.findByIdOrNull(roomId)
                ?: throw RoomNotFoundException(CustomExceptionCode.ROOM_NOT_FOUND)

        return Room(
                roomId = roomEntity.roomId,
                userId = roomEntity.userId,
                roomName = roomEntity.roomName,
                roomDesc = roomEntity.roomDesc,
                createdAt = roomEntity.createdAt,
                roomType = roomEntity.roomType,
        )
    }


}