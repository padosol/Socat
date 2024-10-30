package com.chatservice.domain.dto

data class ChatMessageDTO(
    val type: MessageType,
    val roomId: String,
    val sender: String,
    var message: String
)
