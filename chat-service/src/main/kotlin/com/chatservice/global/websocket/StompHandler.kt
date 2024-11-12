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
        val headerAccessor = StompHeaderAccessor.wrap(message)

        if (StompCommand.CONNECT == headerAccessor.command) {
            // 토큰 검증
//            val jwt = headerAccessor.getFirstNativeHeader("token") as String
//
//            log.info("CONNECT: $jwt")
//            jwtProvider.validateToken(jwt)
        }

        // header 로 부터 token 정보 가져와서 인증처리
        return message
    }



}