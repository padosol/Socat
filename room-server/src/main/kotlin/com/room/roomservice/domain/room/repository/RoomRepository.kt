package com.room.roomservice.domain.room.repository

import com.room.roomservice.domain.room.document.RoomDoc
import com.room.roomservice.domain.room.domain.Room

interface RoomRepository {

    fun save(roomDoc: RoomDoc): Room

    fun findById(roomId: String): Room?

    fun findAll(): List<Room>
}