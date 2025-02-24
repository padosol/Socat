package com.community.communityservice.domain.community.mock

import com.community.communityservice.domain.community.domain.IdGenerator

class TestRoomIdGenerator(
        private val id: String
) : IdGenerator {
    override fun createId(): String {
        return id
    }
}