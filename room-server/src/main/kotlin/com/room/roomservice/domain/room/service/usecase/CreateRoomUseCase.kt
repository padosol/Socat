package com.room.roomservice.domain.room.service.usecase

import com.room.roomservice.domain.room.domain.Room
import com.room.roomservice.domain.room.dto.request.CreateRoomDTO
import jakarta.servlet.http.HttpServletRequest

interface CreateRoomUserCase {
    fun createRoom(createRoomDTO: CreateRoomDTO, userId: String): Room
}