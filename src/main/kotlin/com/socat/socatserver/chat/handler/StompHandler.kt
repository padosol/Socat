package com.socat.socatserver.chat.handler

import org.springframework.messaging.Message
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.simp.stomp.StompHeaderAccessor.*
import org.springframework.messaging.support.ChannelInterceptor
import org.springframework.stereotype.Component

@Component
class StompHandler : ChannelInterceptor{

    override fun postSend(message: Message<*>, channel: MessageChannel, sent: Boolean) {

        val wrap = wrap(message)

        val sessionId = wrap.sessionId
        val command = wrap.command

        // CONNECTION 일때 유저체크

    }


}