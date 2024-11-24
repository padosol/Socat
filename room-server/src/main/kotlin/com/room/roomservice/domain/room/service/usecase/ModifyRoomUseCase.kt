package com.room.roomservice.domain.room.service.usecase

import com.room.roomservice.domain.room.domain.Room
import com.room.roomservice.domain.room.dto.response.RoomResponse

interface ModifyRoomUseCase {
    fun modify(room: Room): RoomResponse
}