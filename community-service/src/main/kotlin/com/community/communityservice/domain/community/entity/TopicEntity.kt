package com.community.communityservice.domain.community.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "topics")
class TopicEntity(
    @Id
    val topicId: String,
    var topicName: String,
)