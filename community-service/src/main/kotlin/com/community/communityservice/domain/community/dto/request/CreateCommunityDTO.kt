package com.community.communityservice.domain.community.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.util.StringUtils

data class CreateCommunityDTO(
        @field:Schema(description = "방 제목", defaultValue = "테스트 룸")
        val roomName: String,
        val roomDesc: String,
        val roomType: String,
) {
    init {
        require(StringUtils.hasText(roomName))
    }
}