package com.community.communityservice.domain.community.repository.community.impl

import com.community.communityservice.domain.community.domain.Community
import com.community.communityservice.domain.community.exception.CommunityNotFoundException
import com.community.communityservice.domain.community.mapper.CommunityMapper
import com.community.communityservice.domain.community.repository.community.CommunityJpaRepository
import com.community.communityservice.domain.community.repository.community.CommunityRepository
import com.community.communityservice.global.exception.CustomExceptionCode
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class CommunityRepositoryImpl(
    private val communityJpaRepository: CommunityJpaRepository
): CommunityRepository {
    override fun save(community: Community): Community {
        val save = communityJpaRepository.save(CommunityMapper.domainToEntity(community))

        return CommunityMapper.entityToDomain(save)
    }

    override fun findById(roomId: String): Community? {
        return communityJpaRepository.findByIdOrNull(roomId)
            ?.let {CommunityMapper.entityToDomain(it)}
    }

    override fun findAll(type: String): List<Community> {


        return  communityJpaRepository.findAll().map {
            CommunityMapper.entityToDomain(it)
        }
    }

    override fun delete(roomId: String) {
        communityJpaRepository.deleteById(roomId)
    }

    override fun findRoomByRoomIdAndUserId(roomId: String, userId: String): Community {
        val communityEntity = communityJpaRepository.findByIdOrNull(roomId)
                ?: throw CommunityNotFoundException(CustomExceptionCode.COMMUNITY_NOT_FOUND)

        return CommunityMapper.entityToDomain(communityEntity)
    }


}