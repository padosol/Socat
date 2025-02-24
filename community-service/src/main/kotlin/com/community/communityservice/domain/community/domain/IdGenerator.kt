package com.community.communityservice.domain.community.domain

interface IdGenerator {
    fun createId(): String
}