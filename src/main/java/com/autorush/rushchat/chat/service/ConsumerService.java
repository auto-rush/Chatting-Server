package com.autorush.rushchat.chat.service;

import com.autorush.rushchat.chat.entity.Message;
import com.autorush.rushchat.config.kafka.KafkaConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsumerService {
    private final SimpMessagingTemplate template;

    @KafkaListener(topics = KafkaConstants.KAFKA_TOPIC, groupId = KafkaConstants.CONSUMER_GROUP_ID)
    public void consumerMessage(Message message) {
        log.info("> chatting : " + message.getContents());
        template.convertAndSend("/topic/group", message);
    }
}
