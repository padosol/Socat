package com.room.roomservice.domain.room.client

import com.room.roomservice.domain.room.vo.ChatResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(name = "chat-service")
interface ChatServiceClient {

    @GetMapping("/chat-service/rooms/{roomId}/chats")
    fun getChats(@PathVariable("roomId") roomId: String): MutableList<ChatResponse>
}