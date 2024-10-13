package com.socat.socatserver.room.service

import com.socat.socatserver.room.domain.Room
import com.socat.socatserver.room.dto.response.RoomResponse

interface RoomService {
    fun createRoom(room: Room): RoomResponse

    fun findRoom(roomId: String): RoomResponse

    fun findAllRoom(): List<RoomResponse>
}