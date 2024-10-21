package com.room.roomservice.chat.dto.request

data class ChatMessageDTO(
    var roomId: String,
    var writer: String,
    var message: String?
)