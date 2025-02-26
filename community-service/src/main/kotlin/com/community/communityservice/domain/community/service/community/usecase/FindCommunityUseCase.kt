package com.community.communityservice.domain.community.service.community.usecase

import com.community.communityservice.domain.community.domain.Community

interface FindCommunityUseCase {
    fun findById(roomId: String): Community

    fun findAll(type: String): List<Community>
}