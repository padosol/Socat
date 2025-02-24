package com.community.communityservice.domain.community.vo

import java.time.LocalDateTime

data class PostResponse(
    val postId: String,
    val userId: String,
    val title: String,
    val content: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime?
)