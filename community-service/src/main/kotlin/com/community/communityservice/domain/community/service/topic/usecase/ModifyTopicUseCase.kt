package com.community.communityservice.domain.community.service.topic.usecase

import com.community.communityservice.domain.community.domain.Topic
import com.community.communityservice.domain.community.dto.request.topic.ModifyTopicDTO

interface ModifyTopicUseCase {
    fun modify(modifyTopicDTO: ModifyTopicDTO): Topic
}