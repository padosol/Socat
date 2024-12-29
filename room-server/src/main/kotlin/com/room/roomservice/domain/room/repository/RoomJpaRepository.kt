package com.room.roomservice.domain.room.repository

import com.room.roomservice.domain.room.entity.RoomEntity
import org.springframework.data.jpa.repository.JpaRepository

interface RoomJpaRepository: JpaRepository<RoomEntity, String> {
}