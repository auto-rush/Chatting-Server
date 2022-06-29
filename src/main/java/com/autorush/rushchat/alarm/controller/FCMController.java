package com.autorush.rushchat.alarm.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class FCMController {

    //웹사이트 최초 접속 시 동작
    @GetMapping("")
    public String permitAlarm() {
        return "alarmInit";
    }

}