package com.community.communityservice.domain.community.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "community")
class CommunityEntity(
    @Id
    val communityId: String,
    val userId: String,
    val communityName: String,
    val communityDesc: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="topicId", referencedColumnName = "topicId")
    val topic: TopicEntity,
) {

}