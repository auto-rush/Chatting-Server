package com.autorush.rushchat.alarm.controller;

import com.autorush.rushchat.alarm.DTO.BasicDTO;
import com.autorush.rushchat.alarm.DTO.SendToGroupDTO;
import com.autorush.rushchat.alarm.DTO.SendToPersonDTO;
import com.autorush.rushchat.alarm.TempGroup;
import com.autorush.rushchat.config.SecurityConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@WebMvcTest(value = FCMRestController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfiguration.class)
})
class FCMRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private static final String BASE_URL = "/api/v1";

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${alarm.sdk.json.path}")
    private String FIREBASE_SDK_JSON_PATH;

//    @Autowired
//    AlarmInitializer alarmInitializer;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
//        alarmInitializer.init();
        try{
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(new ClassPathResource(FIREBASE_SDK_JSON_PATH).getInputStream())).build();
            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("유저 한 명에게 알람 보내기")
    @WithMockUser(roles = "USER", username = "test")
    void send_message_one_user() throws Exception {
        TempGroup tempGroup = new TempGroup();
        int serviceUserCnt = tempGroup.registrationTokens.size();
        assertThat(serviceUserCnt).isGreaterThan(0);

        SendToPersonDTO request = SendToPersonDTO.builder()
                .notification(BasicDTO.builder()
                        .title("제목 테스트").body("내용 테스트").build())
                .to(tempGroup.registrationTokens.get(serviceUserCnt - 1))
                .build();

        mockMvc.perform(post(BASE_URL + "/send")
                .with(csrf()) //post 보낼 시 필요
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(1))
                .andExpect(jsonPath("$.failure").value(0));

    }

    @Test
    @DisplayName("여러 유저에게 'test' 토픽 보내기")
    @WithMockUser(roles = "USER", username = "test")
    void send_topic_users() throws Exception {

        SendToGroupDTO request = SendToGroupDTO.builder()
                .topic("test")
                .data("test-content")
                .build();

        mockMvc.perform(post(BASE_URL + "/send-topic")
                .with(csrf()) //post 보낼 시 필요
                .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk());

    }

}