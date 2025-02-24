package com.community.communityservice.domain.community.service.usecase

interface RemoveCommunityUseCase {
    fun remove(roomId: String, userId: String)
}