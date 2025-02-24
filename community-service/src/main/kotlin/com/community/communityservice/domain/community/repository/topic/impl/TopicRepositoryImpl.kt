package com.community.communityservice.domain.community.repository.topic.impl

import com.community.communityservice.domain.community.domain.Topic
import com.community.communityservice.domain.community.repository.topic.TopicJpaRepository
import com.community.communityservice.domain.community.repository.topic.TopicRepository
import org.springframework.stereotype.Repository

@Repository
class TopicRepositoryImpl(
    private val topicJpaRepository: TopicJpaRepository
) : TopicRepository {
    override fun save(topic: Topic): Topic {
        TODO("Not yet implemented")
    }

    override fun findById(topicId: String): Topic {
        TODO("Not yet implemented")
    }

    override fun findAll(): List<Topic> {
        TODO("Not yet implemented")
    }

    override fun delete(topicId: String) {
        TODO("Not yet implemented")
    }

    override fun modify(topic: Topic): Topic {
        TODO("Not yet implemented")
    }


}