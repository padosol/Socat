package com.room.roomservice.domain.room.domain

import com.room.roomservice.domain.room.dto.request.ModifyRoomDTO
import com.room.roomservice.domain.room.dto.response.RoomResponse
import com.room.roomservice.domain.room.entity.RoomEntity
import com.room.roomservice.domain.room.vo.PostResponse
import java.time.LocalDateTime

class Room(
    val roomId: String,
    val userId: String,
    var roomName: String,
    var roomDesc: String?,
    var createdAt: LocalDateTime,
    var updatedAt: LocalDateTime? = null,
    var posts: MutableList<PostResponse> = mutableListOf(),
    var roomType: String,
){

    companion object {
        fun create(userId: String, roomName: String, roomDesc: String, roomType: String, idGenerator: IdGenerator): Room {
            return Room(
                userId = userId,
                roomId = idGenerator.createId(),
                roomName = roomName,
                roomDesc = roomDesc,
                createdAt = LocalDateTime.now(),
                roomType = roomType,
            )
        }
    }

    fun modifyRoom(modifyRoomDTO: ModifyRoomDTO) {
        roomName = modifyRoomDTO.roomName
        updatedAt = LocalDateTime.now()
    }

    fun toDto(): RoomResponse {
        return RoomResponse(
            roomId = roomId ,
            userId = userId ,
            roomName = roomName ,
            createdAt = createdAt,
            roomType = roomType,
            roomDesc = roomDesc,
            posts = posts
        )
    }

    fun toEntity(): RoomEntity {
        return RoomEntity(
                roomId = roomId,
                userId = userId,
                roomName = roomName,
                roomDesc = roomDesc,
                createdAt = createdAt,
                updatedAt = updatedAt,
                roomType = roomType,
        )
    }

    fun equalsUserId(userId: String): Boolean {
        return this.userId == userId
    }

    fun addPosts(posts: List<PostResponse>) {
        this.posts.addAll(posts)
    }


}