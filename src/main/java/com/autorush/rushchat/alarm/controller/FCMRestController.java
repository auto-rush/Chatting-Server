package com.autorush.rushchat.alarm.controller;

import com.autorush.rushchat.alarm.DTO.BasicDTO;
import com.autorush.rushchat.alarm.DTO.SendDTO;
import com.autorush.rushchat.common.exception.ErrorResponseEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

import static com.autorush.rushchat.common.exception.ErrorCode.EXTERNAL_SERVER_ERROR;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1")
public class FCMRestController {

    @Value("${alarm.bearer_auth}")
    private String bearerAuth;

    private final ObjectMapper objectMapper;

    @PostMapping("/send")
    public Object sendMessage(@RequestBody SendDTO sendDTO) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(bearerAuth);
        headers.set("Content-Type", "application/json");
        String strUri = "https://fcm.googleapis.com/fcm/send";

        RestTemplate restTemplate = new RestTemplate();

        try {
            HttpEntity<String> request =
                    new HttpEntity<>(objectMapper.writeValueAsString(sendDTO), headers);
            ResponseEntity<String> response = restTemplate.postForEntity(new URI(strUri), request, String.class);
            return response;
        } catch (JsonProcessingException jsonProcessingException) {
            System.out.println(jsonProcessingException.getMessage());
        } catch (URISyntaxException urlException) {
            System.out.println(urlException.getMessage());
        }

        return ErrorResponseEntity.toResponseEntity(EXTERNAL_SERVER_ERROR);
    }

    @PostMapping("/send-topic")
    public ResponseEntity<String> sendWithTopic(@RequestBody BasicDTO basicDTO) {
        String topic = "test";

        Message message = Message.builder()
                .putData("score", "850")
                .putData("time", "2:45")
                .setTopic(topic)
                .build();
        try {
            String response = FirebaseMessaging.getInstance().send(message);
            System.out.println("Successfully sent message: " + response);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(HttpStatus.OK);

    }
}
