package com.room.roomservice.domain.room.repository

import com.room.roomservice.domain.room.document.RoomDoc
import org.springframework.data.mongodb.repository.MongoRepository

interface RoomMongoRepository: MongoRepository<RoomDoc, String> {

    fun findByRoomIdAndUserId(roomId: String, userId: String): RoomDoc?
}