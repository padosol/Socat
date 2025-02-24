package com.community.communityservice.domain.community.domain

import java.util.*

class DefaultIdGenerator: IdGenerator {

    override fun createId(): String {
        return UUID.randomUUID().toString()
    }
}