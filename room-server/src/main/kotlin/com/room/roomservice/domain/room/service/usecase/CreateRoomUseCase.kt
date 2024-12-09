package com.room.roomservice.domain.room.service.usecase

import com.room.roomservice.domain.room.domain.Room
import com.room.roomservice.domain.room.dto.request.CreateRoomDTO

interface CreateRoomUseCase {
    fun createRoom(createRoomDTO: CreateRoomDTO, userId: String): Room
}