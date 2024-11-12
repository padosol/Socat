package com.chatservice.domain.service

import com.chatservice.domain.dto.ChatRoomDTO
import com.chatservice.domain.repository.ChatRoomRepository
import com.chatservice.domain.service.usecase.CreateRoomUseCase
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import java.io.IOException
import java.util.*
import java.util.UUID.*

@Service
class ChatService(
    private val objectMapper: ObjectMapper,
    private val chatRoomRepository: ChatRoomRepository,
) : CreateRoomUseCase{
    private val log = LoggerFactory.getLogger(this::class.java)
    private val chatRooms: MutableMap<String, ChatRoomDTO> = mutableMapOf()

    fun findAllRoom(): MutableList<ChatRoomDTO> {
        return chatRooms.values.toMutableList()
    }

    fun findRoomById(roomId: String): ChatRoomDTO? {
        return chatRooms[roomId]
    }

    override fun createRoom(name: String): ChatRoomDTO {
        val roomId = randomUUID().toString()

        val chatRoomDTO = ChatRoomDTO(
            roomId = roomId,
            name = name
        )

        chatRoomRepository.createChatRoom(name)

        return chatRoomDTO
    }

    fun <T> sendMessage(session: WebSocketSession, message: T){
        try {
            session.sendMessage(TextMessage(objectMapper.writeValueAsString(message)))
        } catch(e: IOException) {
            log.error(e.message, e)
        }
    }

}