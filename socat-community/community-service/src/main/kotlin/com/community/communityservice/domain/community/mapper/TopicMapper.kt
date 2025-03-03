package com.community.communityservice.domain.community.mapper

import com.community.communityservice.domain.community.domain.Topic
import com.community.communityservice.domain.community.dto.response.topic.TopicResponse
import com.community.communityservice.domain.community.entity.TopicEntity

class TopicMapper {

    companion object {
        fun domainToResponse(topic: Topic): TopicResponse {
            return TopicResponse(
                topicId = topic.topicId,
                topicName = topic.topicName,
            )
        }

        fun domainToEntity(topic: Topic): TopicEntity {
            return TopicEntity(
                topicId = topic.topicId,
                topicName = topic.topicName,
            )
        }

        fun entityToDomain(topicEntity: TopicEntity): Topic {
            return Topic(
                topicId = topicEntity.topicId,
                topicName = topicEntity.topicName,
            )
        }
    }
}