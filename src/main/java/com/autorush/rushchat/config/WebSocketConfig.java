package com.autorush.rushchat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    /**
     * Client 측이 websocket 연결 할 때 사용할 API 경로를 설정해주는 메서드
     * Hand-shake 커넥션을 생성할 때 사용됨(var sock = new SockJS("/ws/chat"))
     *
     * @param registry STOMP Endpoint 등록
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws-chat").setAllowedOrigins("*").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 브로커가 체크할 Prefix 경로 설정(1:n)
        registry.enableSimpleBroker("/topic");
        // 브로커로 보낼 수 있는 클라이언트 측 목적지 주소
        registry.enableSimpleBroker("/app");
    }
}