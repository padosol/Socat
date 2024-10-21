package com.room.roomservice.domain.room.service

import com.room.roomservice.domain.room.domain.Room
import com.room.roomservice.domain.room.dto.response.RoomResponse

interface RoomService {
    fun createRoom(room: Room): RoomResponse

    fun findRoom(roomId: String): RoomResponse

    fun findAllRoom(): List<RoomResponse>
}