package com.community.communityservice.domain.community.service.usecase

import com.community.communityservice.domain.community.domain.Community
import com.community.communityservice.domain.community.dto.request.ModifyCommunityDTO

interface ModifyCommunityUseCase {
    fun modify(modifyCommunityDTO: ModifyCommunityDTO, userId: String): Community
}