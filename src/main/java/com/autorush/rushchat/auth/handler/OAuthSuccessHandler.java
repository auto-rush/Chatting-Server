package com.autorush.rushchat.auth.handler;

import com.autorush.rushchat.auth.dto.OAuthUserDto;
import com.autorush.rushchat.auth.dto.Token;
import com.autorush.rushchat.auth.service.TokenService;
import com.autorush.rushchat.member.type.Role;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private final TokenService tokenService;
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User principal = (OAuth2User) authentication.getPrincipal();
        Map<String, Object> attributes = principal.getAttributes();

        // CustomOAuth2UserService 에서 UserDto.toMap 해서 넘겼기 때문에 키 값이 고정이다.
        OAuthUserDto userDto = OAuthUserDto.builder()
            .registeredPlatform((String) attributes.get("registrationId"))
            .oAuthId(String.valueOf(attributes.get("id"))) // Integer
            .name((String) attributes.get("name"))
            .email((String) attributes.get("email"))
            .profileImage((String) attributes.get("image"))
            .build();

        Token token = tokenService.generateToken(userDto.getRegisteredPlatform(), userDto.getOAuthId(), Role.ROLE_USER);
        log.info(token.getToken());

        Cookie jwtCookie = new Cookie("jwt", token.getToken());
        jwtCookie.setMaxAge(-1); // 음수이면 브라우저를 닫으면 날아간다.
        jwtCookie.setPath("/");
        response.addCookie(jwtCookie);

        // 기존의 redirect 수행
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
