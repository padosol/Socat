package com.chatservice.domain.controller

import com.chatservice.domain.dto.ChatMessageDTO
import com.chatservice.domain.dto.ChatRoomDTO
import com.chatservice.domain.dto.MessageType
import com.chatservice.domain.repository.ChatRoomRepository
import com.chatservice.domain.service.ChatService
import com.chatservice.domain.service.RedisPublisher
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.simp.SimpMessageSendingOperations
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class ChatController(
    private val redisPublisher: RedisPublisher,
    private val chatRoomRepository: ChatRoomRepository
) {

    @MessageMapping("/chat/message")
    fun message(message: ChatMessageDTO) {
        if (message.type == MessageType.JOIN) {
            chatRoomRepository.enterChatRoom(message.roomId)
            message.message = "${message.sender} 님이 입장하셨습니다."
        }

        redisPublisher.publish(chatRoomRepository.getTopic(message.roomId), message)

//        template.convertAndSend("/sub/chat/room/${message.roomId}", message)
    }
}