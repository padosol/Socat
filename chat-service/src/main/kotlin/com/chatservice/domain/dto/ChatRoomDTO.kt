package com.chatservice.domain.dto

import com.chatservice.domain.service.ChatService
import org.springframework.web.socket.WebSocketSession

class ChatRoomDTO(
    val roomId: String,
    val name: String,
    val sessions: MutableSet<WebSocketSession> = mutableSetOf()
) {

    fun handleActions(session: WebSocketSession, chatMessageDTO: ChatMessageDTO, chatService: ChatService) {
        if (chatMessageDTO.type == MessageType.JOIN) {
            sessions.add(session)
            chatMessageDTO.message = "${chatMessageDTO.sender} 님이 입장하셨습니다."
        }

        sendMessage(chatMessageDTO, chatService)
    }

    fun <T> sendMessage(message: T, chatService: ChatService) {
        sessions.parallelStream().forEach{ session -> chatService.sendMessage(session, message)}
    }

}