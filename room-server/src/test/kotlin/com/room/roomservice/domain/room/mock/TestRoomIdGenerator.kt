package com.room.roomservice.domain.room.mock

import com.room.roomservice.domain.room.domain.IdGenerator
import com.room.roomservice.domain.room.domain.RoomIdGenerator

class TestRoomIdGenerator(
        private val id: String
) : IdGenerator {
    override fun createId(): String {
        return id
    }
}