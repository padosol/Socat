package com.room.roomservice.domain.room.service.usecase

import com.room.roomservice.domain.room.domain.Room

interface FindRoomUseCase {
    fun findRoomById(roomId: String): Room

    fun findAllRoom(): List<Room>
}