//package com.autorush.rushchat.chat.service;
//
//import com.autorush.rushchat.alarm.controller.FCMRestController;
//import com.autorush.rushchat.config.SecurityConfiguration;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.FilterType;
//import org.springframework.test.context.junit4.SpringRunner;
//
//@RunWith(SpringRunner.class)
//@WebMvcTest(value = FCMRestController.class, excludeFilters = {
//        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfiguration.class)
//})
//class ChatServiceTest {
//
//    @Autowired
//    private ChatService chatService;
//
//
//}