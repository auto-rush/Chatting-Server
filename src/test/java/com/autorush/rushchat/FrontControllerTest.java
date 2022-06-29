package com.autorush.rushchat;

import com.autorush.rushchat.config.SecurityConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = FrontController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfiguration.class)
})
public class FrontControllerTest {
    @Autowired
    private WebApplicationContext context;

    MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithAnonymousUser
    @DisplayName("미인증 사용자 home 요청")
    public void homeUnauthorizedUser() throws Exception {
        mockMvc.perform(get("/home"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(roles = "USER", username = "test")
    @DisplayName("인증 사용자 home 요청 시 model 값 정상 반영")
    public void homeTestUserApi() throws Exception {
        mockMvc.perform(get("/home"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(view().name("home"))
                .andExpect(model().attribute("name", "test"));
    }
}