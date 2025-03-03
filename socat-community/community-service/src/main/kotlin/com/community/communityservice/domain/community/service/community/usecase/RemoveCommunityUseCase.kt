package com.community.communityservice.domain.community.service.community.usecase

interface RemoveCommunityUseCase {
    fun remove(communityId: String, userId: String)
}