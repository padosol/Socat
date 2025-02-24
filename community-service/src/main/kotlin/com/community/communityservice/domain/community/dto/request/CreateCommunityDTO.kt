package com.community.communityservice.domain.community.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.util.StringUtils

data class CreateCommunityDTO(
        val communityName: String,
        val communityDesc: String,
        val communityTopic: String,
) {
    init {
        require(StringUtils.hasText(communityName))
    }
}