package com.autorush.rushchat.alarm.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class FCMController {


    @GetMapping("alarm")
    public String permitAlarm() {
        return "alarmInit";
    }

}