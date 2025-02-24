package com.community.communityservice.domain.community.mapper

import com.community.communityservice.domain.community.domain.Topic
import com.community.communityservice.domain.community.dto.response.topic.TopicResponse

class TopicMapper {

    companion object {
        fun domainToResponse(topic: Topic): TopicResponse {
            return TopicResponse(
                topicId = topic.topicId,
                topicName = topic.topicName,
            )
        }
    }
}