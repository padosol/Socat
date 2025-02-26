package com.community.communityservice.domain.community.dto.request

import org.springframework.util.StringUtils

data class CreateCommunityDTO(
        val communityName: String,
        val communityDesc: String,
        val topicId: String,
) {
    init {
        require(StringUtils.hasText(communityName))
    }
}