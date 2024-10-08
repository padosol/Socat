package com.socat.socatserver.chat.handler

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler

@Component
class ChatHandler: TextWebSocketHandler() {
    private val clients: MutableList<WebSocketSession> = mutableListOf<WebSocketSession>()
    private val log: Logger = LoggerFactory.getLogger(ChatHandler::class.java)

    // 메시지 전송
    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        val payload = message.payload

        log.info("payload : $payload")

        for (client in clients) {
            client.sendMessage(message)
        }
    }

    /* 접속 */
    override fun afterConnectionEstablished(session: WebSocketSession) {

        clients.add(session)
        
        log.info("$session 클라이언트 접속")
    }

    /* 해제 */
    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        clients.remove(session)
        
        log.info("$session 클라이언트 접속 해제")
    }

}