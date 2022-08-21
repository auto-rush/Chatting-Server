package com.autorush.rushchat.chat.component;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Comsumers {
    @KafkaListener(topics = "rush")
    public void listenGroup(String message){
        System.out.println("received message : " + message);
    }
}
