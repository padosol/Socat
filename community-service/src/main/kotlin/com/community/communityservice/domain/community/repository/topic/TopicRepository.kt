package com.community.communityservice.domain.community.repository.topic

import com.community.communityservice.domain.community.domain.Community
import com.community.communityservice.domain.community.domain.Topic

interface TopicRepository {

    fun save(topic: Topic): Topic

    fun findById(topicId: String): Topic

    fun findAll(): List<Topic>

    fun delete(topicId: String)

    fun modify(topic: Topic): Topic

}