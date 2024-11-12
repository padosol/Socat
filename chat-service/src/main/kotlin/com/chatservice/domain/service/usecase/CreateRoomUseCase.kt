package com.chatservice.domain.service.usecase

import com.chatservice.domain.dto.ChatRoomDTO

interface CreateRoomUseCase {

    fun createRoom(name: String): ChatRoomDTO
}