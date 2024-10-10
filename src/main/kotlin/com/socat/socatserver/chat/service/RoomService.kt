package com.socat.socatserver.chat.service

import com.socat.socatserver.chat.domain.Room

interface RoomService {
    fun createRoom(room: Room)
}