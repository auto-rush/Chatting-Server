package com.autorush.rushchat.alarm.controller;

import com.autorush.rushchat.alarm.DTO.BasicDTO;
import com.autorush.rushchat.alarm.DTO.SendDTO;
import com.autorush.rushchat.alarm.TempGroup;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@RestController
@RequestMapping(path = "/v1")
public class FCMRestController {

    @Value("${alarm.bearer_auth}")
    private String bearerAuth;

    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestBody SendDTO sendDTO) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(bearerAuth);
        headers.set("Content-Type", "application/json");
        String strUri = "https://fcm.googleapis.com/fcm/send";

        try {
            ObjectMapper mapper = new ObjectMapper();
            HttpEntity<String> request =
                    new HttpEntity<>(mapper.writeValueAsString(sendDTO), headers);
            ResponseEntity<String> response = restTemplate.postForEntity(new URI(strUri), request, String.class);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @PostMapping("/send-topic")
    public ResponseEntity<String> sendWithTopic(@RequestBody BasicDTO basicDTO) {
        String topic = "test";

        Message message = Message.builder()
                .putData("score", "850")
                .putData("time", "2:45")
                .setTopic(topic)
                .build();
        try{
            String response = FirebaseMessaging.getInstance().send(message);
            System.out.println("Successfully sent message: " + response);
        }catch(Exception e){
            e.printStackTrace();
        }

        return new ResponseEntity<>(HttpStatus.OK);

    }
}
