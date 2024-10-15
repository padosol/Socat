package com.socat.socatserver.room.domain

interface IdGenerator {
    fun createId(): String
}