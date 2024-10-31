package com.chatservice.domain.service

import com.chatservice.domain.dto.ChatMessageDTO
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.stereotype.Service

@Service
class RedisPublisher(
    private val redisTemplate: RedisTemplate<String, Any>
) {
    fun publish(topic: ChannelTopic, message: ChatMessageDTO) {
        redisTemplate.convertAndSend(topic.topic, message)
    }
}