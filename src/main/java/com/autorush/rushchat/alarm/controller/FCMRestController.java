package com.autorush.rushchat.alarm.controller;

import com.autorush.rushchat.alarm.DTO.SendToGroupDTO;
import com.autorush.rushchat.alarm.DTO.SendToPersonDTO;
import com.autorush.rushchat.common.exception.ErrorResponseEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
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

    @Value("${alarm.sdk.json.path}")
    private String FIREBASE_SDK_JSON_PATH;


    @PostMapping("/send")
    public Object sendMessage(@RequestBody SendToPersonDTO sendDTO) {
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
    public Object sendWithTopic(@RequestBody SendToGroupDTO request) {

        String firebaseResponse = null;
        Message message = Message.builder()
                .putData("content", request.getData())
                .setTopic(request.getTopic())
                .build();
        try {
            firebaseResponse = FirebaseMessaging.getInstance().send(message);
            return firebaseResponse;
        } catch (FirebaseMessagingException e) {
            System.out.println(e.getMessage());
        }

        return ErrorResponseEntity.toResponseEntity(EXTERNAL_SERVER_ERROR);

    }
}
