package com.community.communityservice.domain.community.service.topic

import com.community.communityservice.domain.community.domain.Topic
import com.community.communityservice.domain.community.dto.request.topic.CreateTopicDTO
import com.community.communityservice.domain.community.dto.request.topic.ModifyTopicDTO
import com.community.communityservice.domain.community.dto.request.topic.RemoveTopicDTO
import com.community.communityservice.domain.community.service.topic.usecase.CreateTopicUseCase
import com.community.communityservice.domain.community.service.topic.usecase.FIndTopicUseCase
import com.community.communityservice.domain.community.service.topic.usecase.ModifyTopicUseCase
import com.community.communityservice.domain.community.service.topic.usecase.RemoveTopicUseCase
import org.springframework.stereotype.Service

@Service
class TopicService(

) : CreateTopicUseCase, ModifyTopicUseCase, RemoveTopicUseCase, FIndTopicUseCase{
    override fun create(createTopicDTO: CreateTopicDTO): Topic {
        TODO("Not yet implemented")
    }

    override fun findById(topicId: String): Topic {
        TODO("Not yet implemented")
    }

    override fun findAll(): List<Topic> {
        TODO("Not yet implemented")
    }

    override fun modify(modifyTopicDTO: ModifyTopicDTO): Topic {
        TODO("Not yet implemented")
    }

    override fun remove(removeTopicDTO: RemoveTopicDTO) {
        TODO("Not yet implemented")
    }
}