package com.room.roomservice.domain.room.client

import com.room.roomservice.domain.room.vo.ChatResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping

@FeignClient(name = "chat-service")
interface ChatServiceClient {

    @GetMapping("/chat-service/chats/{userId}")
    fun getChats(): List<ChatResponse>
}