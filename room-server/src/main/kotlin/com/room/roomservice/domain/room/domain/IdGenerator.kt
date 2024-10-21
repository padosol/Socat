package com.room.roomservice.domain.room.domain

interface IdGenerator {
    fun createId(): String
}