package com.community.communityservice.domain.community.dto.response

import com.community.communityservice.domain.community.vo.PostResponse
import java.time.LocalDateTime

data class CommunityResponse(
    val communityId: String,
    val userId: String,
    val communityName: String,
    val communityDesc: String?,
    val createdAt: LocalDateTime,
    val communityTopic: String,
    val posts: List<PostResponse> = listOf()
)