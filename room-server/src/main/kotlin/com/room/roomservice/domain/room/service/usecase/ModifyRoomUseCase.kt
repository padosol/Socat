package com.room.roomservice.domain.room.service.usecase

import com.room.roomservice.domain.room.domain.Room
import com.room.roomservice.domain.room.dto.request.ModifyRoomDTO
import com.room.roomservice.domain.room.dto.response.RoomResponse
import jakarta.servlet.http.HttpServletRequest

interface ModifyRoomUseCase {
    fun modify(modifyRoomDTO: ModifyRoomDTO, request: HttpServletRequest): Room
}