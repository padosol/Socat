package com.socat.socatserver.room.repository

import com.socat.socatserver.room.document.RoomDoc
import org.springframework.data.mongodb.repository.MongoRepository

interface RoomMongoRepository: MongoRepository<RoomDoc, String> {
}