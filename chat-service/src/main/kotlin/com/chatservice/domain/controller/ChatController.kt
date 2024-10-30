package com.chatservice.domain.controller

import com.chatservice.domain.dto.ChatMessageDTO
import com.chatservice.domain.dto.ChatRoomDTO
import com.chatservice.domain.dto.MessageType
import com.chatservice.domain.service.ChatService
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/chat")
class ChatController(
    private val chatService: ChatService
) {


    @PostMapping
    fun createRoom(@RequestParam("name") name: String):ChatRoomDTO {
        return chatService.createRoom(name)
    }

    @GetMapping
    fun findAllRoom(): MutableList<ChatRoomDTO> {
        return chatService.findAllRoom()
    }

}