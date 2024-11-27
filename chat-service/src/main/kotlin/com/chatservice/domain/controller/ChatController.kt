package com.chatservice.domain.controller

import com.chatservice.domain.dto.ChatMessageDTO
import com.chatservice.domain.repository.ChatRoomRepository
import com.chatservice.domain.service.RedisPublisher
import org.slf4j.LoggerFactory
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ChatController(
    private val redisPublisher: RedisPublisher,
    private val chatRoomRepository: ChatRoomRepository
) {

    private val log = LoggerFactory.getLogger(this::class.java)

    @MessageMapping("/chat/message")
    fun message(message: ChatMessageDTO) {

        log.info("[Chat Message]: {}", message)

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