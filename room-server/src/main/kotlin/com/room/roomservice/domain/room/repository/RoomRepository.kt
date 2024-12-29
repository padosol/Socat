package com.room.roomservice.domain.room.repository

import com.room.roomservice.domain.room.domain.Room

interface RoomRepository {

    fun save(room: Room): Room

    fun findById(roomId: String): Room?

    fun findAll(): List<Room>

    fun delete(roomId: String)

    fun findRoomByRoomIdAndUserId(roomId: String, userId: String): Room

}