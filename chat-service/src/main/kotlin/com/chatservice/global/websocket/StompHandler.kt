package com.chatservice.global.websocket

import com.chatservice.global.jwt.JwtProvider
import org.slf4j.LoggerFactory
import org.springframework.messaging.Message
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.simp.stomp.StompCommand
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.messaging.support.ChannelInterceptor
import org.springframework.stereotype.Component

@Component
class StompHandler(
    private val jwtProvider: JwtProvider
) : ChannelInterceptor {

    private val log = LoggerFactory.getLogger(this::class.java)

    override fun preSend(message: Message<*>, channel: MessageChannel): Message<*>? {
        val headerAccessor: StompHeaderAccessor = StompHeaderAccessor.wrap(message)

        if (StompCommand.SEND == headerAccessor.command) {

            // 로그인 후 채팅 가능
//            val jwt = headerAccessor.getNativeHeader("Authorization")?.get(0) ?: ""
//            if(jwtProvider.validateToken(jwt)) {
//
//            }

        }

        return message
    }
}