package com.socat.socatserver.room.repository

import com.socat.socatserver.room.document.RoomDoc
import com.socat.socatserver.room.domain.Room

interface RoomRepository {

    fun save(roomDoc: RoomDoc): Room

    fun findById(roomId: String): Room?

    fun findAll(): List<Room>
}