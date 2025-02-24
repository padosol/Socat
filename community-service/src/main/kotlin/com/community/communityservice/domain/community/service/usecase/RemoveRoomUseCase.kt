package com.community.communityservice.domain.community.service.usecase

interface RemoveRoomUseCase {
    fun remove(roomId: String, userId: String)
}