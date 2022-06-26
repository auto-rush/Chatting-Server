package com.autorush.rushchat;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class FrontController {
    @GetMapping("/home")
    public String home(Model model, @AuthenticationPrincipal OAuth2User principal) {
        if(principal == null) {
            // 일반 로그인
            model.addAttribute("name", SecurityContextHolder.getContext().getAuthentication().getName());
        } else {
            // OAuth 로그인
            model.addAttribute("name", principal.getAttribute("name"));
        }

        return "home";
    }
}
