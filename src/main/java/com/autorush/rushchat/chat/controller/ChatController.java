package com.autorush.rushchat.chat.controller;

import com.autorush.rushchat.chat.entity.Message;
import com.autorush.rushchat.chat.service.ProducerService;
import com.autorush.rushchat.common.response.ApiResponse;
import com.autorush.rushchat.common.response.EmptyJsonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ProducerService producerService;

    @PostMapping(value = "/api/send", consumes = "application/json", produces = "application/json")
    public ApiResponse<EmptyJsonResponse> sendMessage(@RequestBody Message message) {
        producerService.sendMessage(message);
        return ApiResponse.success();
    }
}