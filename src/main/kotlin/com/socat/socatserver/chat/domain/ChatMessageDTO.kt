package com.socat.socatserver.chat.domain

data class ChatMessageDTO(
    var roomId: String,
    var writer: String,
    var message: String?
)