package com.chatservice.domain.service

import com.chatservice.domain.dto.ChatMessageDTO
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.data.redis.connection.Message
import org.springframework.data.redis.connection.MessageListener
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.messaging.simp.SimpMessageSendingOperations
import org.springframework.stereotype.Service
import java.lang.Exception

@Service
class RedisSubscriber(
    private val objectMapper: ObjectMapper,
    private val redisTemplate: RedisTemplate<String, Any>,
    private val messageTemplate: SimpMessageSendingOperations
) : MessageListener{

    private val log = LoggerFactory.getLogger(this::class.java)

    override fun onMessage(message: Message, pattern: ByteArray?) {
        try {
            val publishMessage = redisTemplate.stringSerializer.deserialize(message.body) as String
            val roomMessage = objectMapper.readValue(publishMessage, ChatMessageDTO::class.java)

            messageTemplate.convertAndSend("/sub/chat/room/${roomMessage.roomId}", roomMessage)
        } catch(e: Exception) {
            log.error(e.message)
        }
    }
}