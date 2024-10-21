package com.room.roomservice.config

import com.room.roomservice.chat.handler.StompHandler
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.simp.config.ChannelRegistration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer

@Configuration
@EnableWebSocketMessageBroker
class WebSocketConfig(
    private val stompHandler: StompHandler
): WebSocketMessageBrokerConfigurer {
    
    // stomp 를 사용하면 세션 관리를 하지 않아도 됨
    override fun registerStompEndpoints(registry: StompEndpointRegistry) {
        registry.addEndpoint("/ws/stomp")  // ws, stomp endpoint 연결시 사용
            .setAllowedOrigins("*")
//            .withSockJS()
    }

    override fun configureMessageBroker(registry: MessageBrokerRegistry) {
        registry.setApplicationDestinationPrefixes("/pub") // Client 에서 오는 SEND 요청을 처리
        registry.enableSimpleBroker("/sub") // 서버측에서 클라이언트 측으로 메시지 전송
    }

    override fun configureClientInboundChannel(registration: ChannelRegistration) {
        registration.interceptors(stompHandler)
    }

}