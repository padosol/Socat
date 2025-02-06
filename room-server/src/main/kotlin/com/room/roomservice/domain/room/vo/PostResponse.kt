package com.room.roomservice.domain.room.vo

import java.time.LocalDateTime

data class PostResponse(
    val postId: String,
    val userId: String,
    val title: String,
    val content: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime?
)