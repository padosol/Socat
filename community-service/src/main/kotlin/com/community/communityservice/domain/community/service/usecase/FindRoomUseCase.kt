package com.community.communityservice.domain.community.service.usecase

import com.community.communityservice.domain.community.domain.Community

interface FindRoomUseCase {
    fun findRoomById(roomId: String): Community

    fun findAllRoom(): List<Community>
}