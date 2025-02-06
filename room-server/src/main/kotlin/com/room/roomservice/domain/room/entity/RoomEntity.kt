package com.room.roomservice.domain.room.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "room")
class RoomEntity(
    @Id
    val roomId: String,
    val userId: String,
    val roomName: String,
    val roomDesc: String?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime? = null,
    val roomType: String
) {

}