package com.community.communityservice.domain.community.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "community")
class CommunityEntity(
    @Id
    val communityId: String,
    val userId: String,
    val communityName: String,
    val communityDesc: String?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime? = null,
    val communityTopic: String
) {

}