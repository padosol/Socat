package com.chatservice.domain.service

import com.chatservice.domain.dto.ChatMessageDTO
import com.chatservice.domain.dto.ChatRoomDTO
import com.chatservice.domain.repository.ChatRoomRepository
import com.chatservice.domain.service.usecase.EnterChatRoomUseCase
import com.chatservice.domain.service.usecase.LeaveChatRoomUseCase
import org.springframework.data.redis.core.HashOperations
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service

@Service
class ChatService(
        private val chatRoomRepository: ChatRoomRepository,
        private val redisTemplate: RedisTemplate<String, Any>
): EnterChatRoomUseCase, LeaveChatRoomUseCase {

    private val opsHashChatRoom: HashOperations<String, String, ChatRoomDTO> = redisTemplate.opsForHash()
    private val CHAT_ROOMS: String = "CHAT_ROOM"

    override fun enterChatRoom(chatMessageDTO: ChatMessageDTO) {

        val chatRoomDTO = opsHashChatRoom[CHAT_ROOMS, chatMessageDTO.roomId]



        TODO("Not yet implemented")
    }

    override fun leaveChatRoom(chatMessageDTO: ChatMessageDTO) {
        TODO("Not yet implemented")
    }
}