package com.community.communityservice.domain.community.repository.topic.impl

import com.community.communityservice.domain.community.domain.Topic
import com.community.communityservice.domain.community.mapper.TopicMapper
import com.community.communityservice.domain.community.repository.topic.TopicJpaRepository
import com.community.communityservice.domain.community.repository.topic.TopicRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class TopicRepositoryImpl(
    private val topicJpaRepository: TopicJpaRepository
) : TopicRepository {
    override fun save(topic: Topic): Topic {
        val save = topicJpaRepository.save(TopicMapper.domainToEntity(topic))

        return TopicMapper.entityToDomain(save)
    }

    override fun findById(topicId: String): Topic? {
        return topicJpaRepository.findByIdOrNull(topicId)
            ?.let { TopicMapper.entityToDomain(it) }
    }

    override fun findAll(): List<Topic> {
        return topicJpaRepository.findAll().map { TopicMapper.entityToDomain(it) }
    }

    override fun delete(topicId: String) {
        topicJpaRepository.deleteById(topicId)
    }

}