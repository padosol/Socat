package com.community.communityservice.domain.community.service.community.usecase

import com.community.communityservice.domain.community.domain.Community
import com.community.communityservice.domain.community.dto.request.CreateCommunityDTO

interface CreateCommunityUseCase {
    fun create(createCommunityDTO: CreateCommunityDTO, userId: String): Community
}