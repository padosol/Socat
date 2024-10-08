package com.socat.socatserver.chat

import com.socat.socatserver.chat.domain.ChatMessageDTO
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.web.bind.annotation.RestController

@RestController
class ChatController(
    private val template: SimpMessagingTemplate
) {

    @MessageMapping(value = ["/chat/enter"])
    fun enter(message: ChatMessageDTO) {
        message.message = "${message.writer} 님이 채팅방에 입장하셨습니다."
        template.convertAndSend("/sub/chat/room/${message.roomId}", message)
    }

    @MessageMapping("/chat/message")
    fun message(message: ChatMessageDTO) {
        template.convertAndSend("/sub/chat/room/${message.roomId}", message)
    }

}