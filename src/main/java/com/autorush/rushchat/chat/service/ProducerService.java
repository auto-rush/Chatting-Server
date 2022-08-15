package com.autorush.rushchat.chat.service;

import com.autorush.rushchat.chat.entity.Message;
import com.autorush.rushchat.common.exception.CustomException;
import com.autorush.rushchat.config.kafka.KafkaConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProducerService {
    private final KafkaTemplate<String, Message> kafkaTemplate;

    public void sendMessage(Message message) {
        log.info("sender : " + message.getSender() + "\nmessage = " + message.getContents());
        try {
            kafkaTemplate.send(KafkaConstants.KAFKA_TOPIC, message).get();
        } catch (InterruptedException e) {
            log.info("InterruptedException " + e);
            Thread.currentThread().interrupt(); // 현재 thread 를 확실하게 interrupt 한다. (안전하게 종료)
            throw new CustomException();
        } catch (ExecutionException e) {
            log.info("ExecutionException " + e);
            throw new CustomException();
        }
    }
}
