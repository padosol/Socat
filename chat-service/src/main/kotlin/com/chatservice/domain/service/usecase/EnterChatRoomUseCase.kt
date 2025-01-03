package com.chatservice.domain.service.usecase

import com.chatservice.domain.dto.ChatMessageDTO

interface EnterChatRoomUseCase {
    fun enterChatRoom(chatMessageDTO: ChatMessageDTO)
}