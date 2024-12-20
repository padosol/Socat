package com.room.roomservice.domain.room.document

import com.room.roomservice.domain.room.domain.Room
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "room")
class RoomDoc(
    @org.springframework.data.annotation.Id
    val roomId: String,
    val userId: String,
    val roomName: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime? = null
)