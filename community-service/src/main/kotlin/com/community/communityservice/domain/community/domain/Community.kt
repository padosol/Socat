package com.community.communityservice.domain.community.domain

import com.community.communityservice.domain.community.dto.request.ModifyCommunityDTO
import com.community.communityservice.domain.community.dto.response.CommunityResponse
import com.community.communityservice.domain.community.entity.CommunityEntity
import com.community.communityservice.domain.community.vo.PostResponse
import java.time.LocalDateTime

class Community(
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
        fun create(userId: String, roomName: String, roomDesc: String, roomType: String, idGenerator: IdGenerator): Community {
            return Community(
                userId = userId,
                roomId = idGenerator.createId(),
                roomName = roomName,
                roomDesc = roomDesc,
                createdAt = LocalDateTime.now(),
                roomType = roomType,
            )
        }
    }

    fun modifyRoom(modifyRoomDTO: ModifyCommunityDTO) {
        roomName = modifyRoomDTO.roomName
        updatedAt = LocalDateTime.now()
    }

    fun toDto(): CommunityResponse {
        return CommunityResponse(
            roomId = roomId ,
            userId = userId ,
            roomName = roomName ,
            createdAt = createdAt,
            roomType = roomType,
            roomDesc = roomDesc,
            posts = posts
        )
    }

    fun toEntity(): CommunityEntity {
        return CommunityEntity(
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