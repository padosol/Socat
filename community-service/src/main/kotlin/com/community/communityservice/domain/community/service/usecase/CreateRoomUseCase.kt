package com.community.communityservice.domain.community.service.usecase

import com.community.communityservice.domain.community.domain.Community
import com.community.communityservice.domain.community.dto.request.CreateCommunityDTO

interface CreateRoomUseCase {
    fun createRoom(createCommunityDTO: CreateCommunityDTO, userId: String): Community
}