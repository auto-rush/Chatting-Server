package com.autorush.rushchat.chat.controller;


import com.autorush.rushchat.chat.domain.Chat;
import com.autorush.rushchat.chat.repository.ChatRepository;
import com.autorush.rushchat.common.exception.ErrorResponseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.autorush.rushchat.common.exception.ErrorCode.EXTERNAL_SERVER_ERROR;

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
