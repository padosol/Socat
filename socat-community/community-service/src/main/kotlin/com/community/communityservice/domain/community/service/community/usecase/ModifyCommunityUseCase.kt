package com.community.communityservice.domain.community.service.community.usecase

import com.community.communityservice.domain.community.domain.Community
import com.community.communityservice.domain.community.dto.request.ModifyCommunityDTO

interface ModifyCommunityUseCase {
    fun modify(modifyCommunityDTO: ModifyCommunityDTO, userId: String): Community
}