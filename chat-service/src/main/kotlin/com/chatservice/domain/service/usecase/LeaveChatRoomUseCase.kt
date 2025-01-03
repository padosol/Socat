package com.chatservice.domain.service.usecase

import com.chatservice.domain.dto.ChatMessageDTO

interface LeaveChatRoomUseCase {
    fun leaveChatRoom(chatMessageDTO: ChatMessageDTO)
}