package com.autorush.rushchat.chat.component;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

@Component
@RequiredArgsConstructor
public class Producers {
    private final KafkaTemplate<String,String> kafkaTemplate;
    private final Logger logger = LoggerFactory.getLogger(KafkaProducer.class);

    public void sendMessage(String topick, String payload){
        ListenableFuture<SendResult<String,String>> listenable = kafkaTemplate.send(topick,payload);
    }
}
