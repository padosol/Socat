package com.chatservice.global.redis

import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import redis.embedded.RedisServer

@Profile("local") // 로컬일경우 내장 레디스 사용
@Configuration
class EmbeddedRedisConfig(
    @Value("\${spring.data.redis.port:6379}") private val port: Int
) {
    private var redisServer: RedisServer = RedisServer(port)

    @PostConstruct
    private fun startRedis() {
        redisServer.start()
    }

    @PreDestroy
    private fun stopRedis() {
        redisServer.stop()
    }
}