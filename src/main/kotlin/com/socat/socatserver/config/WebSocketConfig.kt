//package com.socat.socatserver.config
//
//import com.socat.socatserver.chat.handler.ChatHandler
//import org.springframework.context.annotation.Configuration
//import org.springframework.messaging.simp.config.MessageBrokerRegistry
//import org.springframework.web.socket.config.annotation.*
//
//@Configuration
//@EnableWebSocket
//class WebSocketConfig(
//    private val chatHandler: ChatHandler
//) : WebSocketConfigurer {
//
//
//    // Websocket 연결을 위한 엔드포인트
////    override fun registerStompEndpoints(registry: StompEndpointRegistry) {
////        registry.addEndpoint("/ws").withSockJS()
////    }
////
////    override fun configureMessageBroker(registry: MessageBrokerRegistry) {
////        // 서버가 목적지 일때
////        registry.setApplicationDestinationPrefixes("/app")
////
////        // 클라이언트가 subscribe 할떄
////        registry.enableSimpleBroker("/topic")
////    }
//    override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
//        registry.addHandler(chatHandler, "ws/chat").setAllowedOrigins("*").withSockJS()
//    }
//
//}