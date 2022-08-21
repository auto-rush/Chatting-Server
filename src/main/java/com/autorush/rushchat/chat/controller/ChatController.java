package com.autorush.rushchat.chat.controller;


//import com.autorush.rushchat.chat.entity.Chat;
//import com.autorush.rushchat.chat.repository.ChatRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping(path = "/api/v1")
//public class ChatController {
//
//    private final ChatRepository chatRepository;
//
//    @GetMapping("/getAll")
//    public ResponseEntity<List<Chat>> test() {
//        List<Chat> chatList = chatRepository.findAll();
//        return new ResponseEntity<>(chatList, HttpStatus.OK);
//
//    }
//}


import com.autorush.rushchat.chat.component.Producers;
import com.autorush.rushchat.chat.constants.KafkaConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping(value = "/kafka")
@RequiredArgsConstructor
public class ChatController {
   private final Producers producers;

   @GetMapping("")
   public void test(){
      producers.sendMessage("rush","test message to rushTopic");
   }
}