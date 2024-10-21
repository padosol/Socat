package com.room.roomservice.domain.room.domain

import java.util.*

class RoomIdGenerator: IdGenerator {

    override fun createId(): String {
        return UUID.randomUUID().toString()
    }
}