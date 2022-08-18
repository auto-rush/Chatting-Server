package com.autorush.rushchat.chat.controller;


import com.autorush.rushchat.chat.entity.Chat;
import com.autorush.rushchat.chat.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1")
public class ChatController {

    private final ChatRepository chatRepository;

    @GetMapping("/getAll")
    public ResponseEntity<List<Chat>> test() {
        List<Chat> chatList = chatRepository.findAll();
        return new ResponseEntity<>(chatList, HttpStatus.OK);

    }
}
