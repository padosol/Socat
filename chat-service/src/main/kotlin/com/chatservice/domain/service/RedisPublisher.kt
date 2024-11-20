package com.chatservice.domain.service

import com.chatservice.domain.document.Chat
import com.chatservice.domain.dto.ChatMessageDTO
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class RedisPublisher(
    private val redisTemplate: RedisTemplate<String, Any>,
    private val mongoTemplate: MongoTemplate
) {
    fun publish(topic: ChannelTopic, message: ChatMessageDTO) {

        mongoTemplate.insert(
                Chat(
                        message = message.message,
                        sender = message.sender,
                        createdAt = LocalDateTime.now(),
                        type = message.type.name,

                )
        )

        redisTemplate.convertAndSend(topic.topic, message)
    }
}