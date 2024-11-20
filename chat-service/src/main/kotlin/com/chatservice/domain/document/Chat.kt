package com.chatservice.domain.document

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "chats")
class Chat(
    @Id
    var id: String? = null,
    val message: String?,
    val sender: String,
    val createdAt: LocalDateTime,
    val type: String
)