package com.room.roomservice.domain.room.service.usecase

import com.room.roomservice.domain.room.domain.Room
import com.room.roomservice.domain.room.dto.response.RoomResponse

interface FindRoomUseCase {
    fun findRoom(roomId: String): Room

    fun findAllRoom(): List<Room>
}