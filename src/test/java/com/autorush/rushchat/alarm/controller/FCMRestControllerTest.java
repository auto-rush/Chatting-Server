package com.autorush.rushchat.alarm.controller;

import com.autorush.rushchat.alarm.DTO.BasicDTO;
import com.autorush.rushchat.alarm.DTO.SendDTO;
import com.autorush.rushchat.alarm.TempGroup;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(FCMRestController.class)
class FCMRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private static final String BASE_URL = "/v1";

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void 특정_서비스유저에게_알림_보내기() throws Exception {
        TempGroup tempGroup = new TempGroup();
        int serviceUserCnt = tempGroup.registrationTokens.size();
        assertThat(serviceUserCnt).isGreaterThan(0);

        SendDTO request = SendDTO.builder()
                .notification(BasicDTO.builder()
                .title("제목 테스트").body("내용 테스트").build())
                .to(tempGroup.registrationTokens.get(serviceUserCnt-1))
                .build();

        mockMvc.perform(post(BASE_URL + "/send")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk());

    }
}