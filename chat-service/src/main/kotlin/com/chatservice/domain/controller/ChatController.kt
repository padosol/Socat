package com.chatservice.domain.controller

import com.chatservice.domain.dto.ChatMessageDTO
import com.chatservice.domain.repository.ChatRoomRepository
import com.chatservice.domain.service.RedisPublisher
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ChatController(
    private val redisPublisher: RedisPublisher,
    private val chatRoomRepository: ChatRoomRepository
) {

    @MessageMapping("/chat/message")
    fun message(message: ChatMessageDTO) {
        if (message.type == ChatMessageDTO.MessageType.JOIN) {
            chatRoomRepository.enterChatRoom(message.roomId)
            message.message = "안내: ${message.sender} 님이 입장하셨습니다."
        }

        if (message.type == ChatMessageDTO.MessageType.LEAVE) {
            chatRoomRepository.enterChatRoom(message.roomId)
            message.message = "안내: ${message.sender} 님이 퇴장하셨습니다."
        }

        // redis 를 통해 동기화함
        redisPublisher.publish(chatRoomRepository.getTopic(message.roomId), message)
    }
}