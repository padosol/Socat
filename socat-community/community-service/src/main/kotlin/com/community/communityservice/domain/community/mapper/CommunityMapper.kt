package com.community.communityservice.domain.community.mapper

import com.community.communityservice.domain.community.domain.Community
import com.community.communityservice.domain.community.dto.response.CommunityResponse
import com.community.communityservice.domain.community.entity.CommunityEntity
import com.community.communityservice.domain.community.vo.PostResponse
import java.time.LocalDateTime

class CommunityMapper {


    companion object {

        fun entityToDomain(communityEntity: CommunityEntity): Community {
            return Community(
                communityId = communityEntity.communityId,
                userId = communityEntity.userId,
                communityName = communityEntity.communityName,
                communityDesc = communityEntity.communityDesc,
                createdAt = communityEntity.createdAt,
                updatedAt = communityEntity.updatedAt,
                topic = TopicMapper.entityToDomain(communityEntity.topic),
            )
        }

        fun domainToEntity(community: Community): CommunityEntity {
            return CommunityEntity(
                communityId = community.communityId,
                userId = community.userId,
                communityName = community.communityName,
                communityDesc = community.communityDesc,
                createdAt = community.createdAt,
                updatedAt = community.updatedAt,
                topic = TopicMapper.domainToEntity(community.topic!!)
            )
        }

        fun domainToDTO(community: Community): CommunityResponse {
            return CommunityResponse(
                communityId = community.communityId,
                userId = community.userId,
                communityName = community.communityName,
                communityDesc = community.communityDesc,
                createdAt = community.createdAt,
                topicId = community.topic.topicId,
                topicName = community.topic.topicName,
                posts = community.posts,
            )
        }

        fun dtoTODomain() {

        }

    }
}