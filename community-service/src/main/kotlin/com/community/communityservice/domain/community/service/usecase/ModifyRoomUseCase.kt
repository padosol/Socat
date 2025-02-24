package com.community.communityservice.domain.community.service.usecase

import com.community.communityservice.domain.community.domain.Community
import com.community.communityservice.domain.community.dto.request.ModifyCommunityDTO

interface ModifyRoomUseCase {
    fun modify(modifyRoomDTO: ModifyCommunityDTO, userId: String): Community
}