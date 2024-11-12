package com.chatservice.domain.domain

import java.time.LocalDateTime

class Room(
    val roomId: String,
    val roomName: String,
    val createdAt: LocalDateTime,
) {

}