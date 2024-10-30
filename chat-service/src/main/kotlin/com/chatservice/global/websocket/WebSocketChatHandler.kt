package com.chatservice.global.websocket

import com.chatservice.domain.dto.ChatMessageDTO
import com.chatservice.domain.service.ChatService
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler

@Component
class WebSocketChatHandler(
    private val objectMapper: ObjectMapper,
    private val chatService: ChatService
) : TextWebSocketHandler(){

    private val log = LoggerFactory.getLogger(this::class.java)

    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        val payload = message.payload

        log.info("payload $payload")

//        val textMessage = TextMessage("Welcome chatting server")
//        session.sendMessage(textMessage)

        val chatMessageDTO = objectMapper.readValue(payload, ChatMessageDTO::class.java)
        val room = chatService.findRoomById(chatMessageDTO.roomId)
                 ?:throw IllegalArgumentException("존재하지 않는 방입니다.")

        room.handleActions(session, chatMessageDTO, chatService)
    }
}