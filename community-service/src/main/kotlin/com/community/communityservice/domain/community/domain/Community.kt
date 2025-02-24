package com.community.communityservice.domain.community.domain

import com.community.communityservice.domain.community.dto.request.ModifyCommunityDTO
import com.community.communityservice.domain.community.dto.response.CommunityResponse
import com.community.communityservice.domain.community.entity.CommunityEntity
import com.community.communityservice.domain.community.vo.PostResponse
import java.time.LocalDateTime

class Community(
    val communityId: String,
    val userId: String,
    var communityName: String,
    var communityDesc: String?,
    var createdAt: LocalDateTime,
    var updatedAt: LocalDateTime? = null,
    var posts: MutableList<PostResponse> = mutableListOf(),
    var communityTopic: String,
){

    companion object {
        fun create(userId: String, communityName: String, communityDesc: String, communityTopic: String, idGenerator: IdGenerator): Community {
            return Community(
                userId = userId,
                communityId = idGenerator.createId(),
                communityName = communityName,
                communityDesc = communityDesc,
                createdAt = LocalDateTime.now(),
                communityTopic = communityTopic,
            )
        }
    }

    fun modifyRoom(modifyCommunityDTO: ModifyCommunityDTO) {
        communityName = modifyCommunityDTO.communityName
        updatedAt = LocalDateTime.now()
    }


    fun equalsUserId(userId: String): Boolean {
        return this.userId == userId
    }

    fun addPosts(posts: List<PostResponse>) {
        this.posts.addAll(posts)
    }


}