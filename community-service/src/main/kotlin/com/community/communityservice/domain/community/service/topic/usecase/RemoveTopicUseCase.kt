package com.community.communityservice.domain.community.service.topic.usecase

import com.community.communityservice.domain.community.domain.Topic
import com.community.communityservice.domain.community.dto.request.topic.RemoveTopicDTO

interface RemoveTopicUseCase {
    fun remove(removeTopicDTO: RemoveTopicDTO)
}