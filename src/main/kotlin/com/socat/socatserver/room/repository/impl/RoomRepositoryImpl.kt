package com.socat.socatserver.room.repository.impl

import com.socat.socatserver.room.document.RoomDoc
import com.socat.socatserver.room.domain.Room
import com.socat.socatserver.room.repository.RoomMongoRepository
import com.socat.socatserver.room.repository.RoomRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class RoomRepositoryImpl(
    private val roomMongoRepository: RoomMongoRepository
): RoomRepository {
    override fun save(roomDoc: RoomDoc): Room {

        val save = roomMongoRepository.save(roomDoc)

        return Room(
            roomId = save.roomId,
            roomName = save.roomName,
            createAt = save.createdAt
        )
    }

    override fun findById(roomId: String): Room? {
        return roomMongoRepository.findByIdOrNull(roomId)
            ?.let {
                Room(
                    roomId = it.roomId,
                    roomName = it.roomName,
                    createAt = it.createdAt
                )
            }
    }

    override fun findAll(): List<Room> {
        TODO("Not yet implemented")
    }
}