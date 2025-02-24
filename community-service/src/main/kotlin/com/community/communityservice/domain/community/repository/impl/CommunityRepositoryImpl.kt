package com.community.communityservice.domain.community.repository.impl

import com.community.communityservice.domain.community.domain.Community
import com.community.communityservice.domain.community.exception.CommunityNotFoundException
import com.community.communityservice.domain.community.repository.CommunityJpaRepository
import com.community.communityservice.domain.community.repository.CommunityRepository
import com.community.communityservice.global.exception.CustomExceptionCode
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class CommunityRepositoryImpl(
    private val communityJpaRepository: CommunityJpaRepository
): CommunityRepository {
    override fun save(community: Community): Community {
        val save = communityJpaRepository.save(community.toEntity())

        return Community(
                userId = save.userId,
                roomId = save.roomId,
                roomName = save.roomName,
                roomDesc = save.roomDesc,
                createdAt = save.createdAt,
                roomType = save.roomType,
        )
    }

    override fun findById(roomId: String): Community? {
        return communityJpaRepository.findByIdOrNull(roomId)
            ?.let {
                Community(
                    userId = it.userId,
                    roomId = it.roomId,
                    roomName = it.roomName,
                    roomDesc = it.roomDesc,
                    createdAt = it.createdAt,
                    roomType = it.roomType,
                )
            }
    }

    override fun findAll(): List<Community> {
        return  communityJpaRepository.findAll().map {
            Community(
                userId = it.userId,
                roomId = it.roomId,
                roomName = it.roomName,
                roomDesc = it.roomDesc,
                createdAt = it.createdAt,
                roomType = it.roomType,
            )
        }
    }

    override fun delete(roomId: String) {
        communityJpaRepository.deleteById(roomId)
    }

    override fun findRoomByRoomIdAndUserId(roomId: String, userId: String): Community {
        val roomEntity = communityJpaRepository.findByIdOrNull(roomId)
                ?: throw CommunityNotFoundException(CustomExceptionCode.ROOM_NOT_FOUND)

        return Community(
                roomId = roomEntity.roomId,
                userId = roomEntity.userId,
                roomName = roomEntity.roomName,
                roomDesc = roomEntity.roomDesc,
                createdAt = roomEntity.createdAt,
                roomType = roomEntity.roomType,
        )
    }


}