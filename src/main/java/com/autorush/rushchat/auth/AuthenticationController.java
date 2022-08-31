package com.autorush.rushchat.auth;

import com.autorush.rushchat.member.entity.Member;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
public class AuthenticationController {

    @GetMapping("/user")
    public Map<String, Object> user(@AuthenticationPrincipal Member principal) {
        return Collections.singletonMap("name", principal.getName());
    }
}