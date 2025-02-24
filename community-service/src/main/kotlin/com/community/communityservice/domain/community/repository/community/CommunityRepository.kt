package com.community.communityservice.domain.community.repository.community

import com.community.communityservice.domain.community.domain.Community

interface CommunityRepository {

    fun save(community: Community): Community

    fun findById(roomId: String): Community?

    fun findAll(): List<Community>

    fun delete(roomId: String)

    fun findRoomByRoomIdAndUserId(roomId: String, userId: String): Community

}