package com.community.communityservice.domain.community.dto.response

import com.community.communityservice.domain.community.vo.PostResponse
import java.time.LocalDateTime

data class CommunityResponse(
    val roomId: String,
    val userId: String,
    val roomName: String,
    val roomDesc: String?,
    val createdAt: LocalDateTime,
    val roomType: String,
    val posts: List<PostResponse> = listOf()
)