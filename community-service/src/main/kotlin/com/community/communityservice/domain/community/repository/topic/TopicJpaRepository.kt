package com.community.communityservice.domain.community.repository.topic

import com.community.communityservice.domain.community.entity.TopicEntity
import org.springframework.data.jpa.repository.JpaRepository

interface TopicJpaRepository : JpaRepository<TopicEntity, Long>{


}