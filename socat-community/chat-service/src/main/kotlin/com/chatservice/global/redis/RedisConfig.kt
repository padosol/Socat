package com.chatservice.global.redis

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.listener.RedisMessageListenerContainer
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
class RedisConfig {


    /**
     * pub/sub listener
     */
    @Bean
    fun redisMessageListener(redisConnectionFactory: RedisConnectionFactory): RedisMessageListenerContainer {
        val redisMessageListenerContainer = RedisMessageListenerContainer()
        redisMessageListenerContainer.setConnectionFactory(redisConnectionFactory)

        return redisMessageListenerContainer
    }

    /**
     * redis template
     */
    @Bean
    fun redisTemplate(redisConnectionFactory: RedisConnectionFactory): RedisTemplate<String, Any> {
        val redisTemplate = RedisTemplate<String, Any>()
        redisTemplate.connectionFactory = redisConnectionFactory
        redisTemplate.keySerializer = StringRedisSerializer()
        redisTemplate.valueSerializer = Jackson2JsonRedisSerializer(String.javaClass)

        return redisTemplate
    }
}