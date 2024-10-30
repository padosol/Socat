package com.chatservice.global.websocket

import org.slf4j.LoggerFactory
import org.springframework.context.event.EventListener
import org.springframework.messaging.Message
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.messaging.support.ChannelInterceptor
import org.springframework.stereotype.Component
import org.springframework.web.socket.messaging.SessionConnectedEvent
import org.springframework.web.socket.messaging.SessionDisconnectEvent

@Component
class StompHandler : ChannelInterceptor {

    private val log = LoggerFactory.getLogger(this::class.java)

    override fun postSend(message: Message<*>, channel: MessageChannel, sent: Boolean) {

        val wrap = StompHeaderAccessor.wrap(message)

        val sessionId = wrap.sessionId
        val command = wrap.command

        // CONNECTION 일때 유저체크
    }

    override fun preSend(message: Message<*>, channel: MessageChannel): Message<*>? {
        val headerAccessor = StompHeaderAccessor.wrap(message)

        // header 로 부터 token 정보 가져와서 인증처리
        return message
    }

    @EventListener
    fun handleWebSocketConnectionListener(event: SessionConnectedEvent) {
        log.info("사용자 입장")
    }

    @EventListener
    fun handleWebSocketDisconnectionListener(event: SessionDisconnectEvent) {
        log.info("사용자 퇴장")
    }

}