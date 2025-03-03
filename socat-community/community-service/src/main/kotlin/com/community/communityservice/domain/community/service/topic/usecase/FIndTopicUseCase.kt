package com.community.communityservice.domain.community.service.topic.usecase

import com.community.communityservice.domain.community.domain.Topic

interface FIndTopicUseCase {
    fun findById(topicId: String): Topic

    fun findAll(): List<Topic>
}