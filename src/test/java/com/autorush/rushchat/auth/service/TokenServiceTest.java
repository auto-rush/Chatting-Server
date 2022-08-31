package com.autorush.rushchat.auth.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.autorush.rushchat.auth.dto.Token;
import com.autorush.rushchat.member.type.Role;
import java.util.Base64;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

@SpringBootTest
class TokenServiceTest {
    @Autowired
    TokenService tokenService;

    @Test
    @DisplayName("private.yml 의 secret key 가 정상적으로 주입되었는지 확인")
    public void checkConfigurationDI() {
        assertThat(tokenService.getSecretKey()).isNotNull();
    }

    @Test
    @DisplayName("jwt 토큰 생성 확인")
    /*
     * JWT 는 .을 기준으로 세 부분을 나뉘고, 각 부분은 BASE64 인코딩이 되어있다.
     * 첫 부분은 암호화 형식을, 두 번째는 claims 을, 세 번째는 서명을 담는다.
     */
    public void generateToken() {
        String registrationId = "github";
        String oAuthId = "1234";
        String algorithm = "HS256";
        Token token = tokenService.generateToken(registrationId, oAuthId, Role.ROLE_USER);
        System.out.println(token.getToken());
        String[] strings = token.getToken().split("\\.");

        assertThat(strings.length).isEqualTo(3);

        String header = new String(Base64.getDecoder().decode(strings[0]));
        String payload = new String(Base64.getDecoder().decode(strings[1]));

        assertThat(header).contains(algorithm);
        assertThat(payload).contains(registrationId).contains(oAuthId).contains(Role.ROLE_USER.name());
    }

    @Test
    @DisplayName("만료된 토큰에 대한 verifyToken 함수 동작 확인")
    /*
     * java reflection 을 사용하 private 한 tokenPeriod 를 잠시 1초로 바꾸었다.
     */
    public void verifyToken() throws InterruptedException {
        Object tokenPeriod = ReflectionTestUtils.getField(tokenService, "tokenPeriod");
        ReflectionTestUtils.setField(tokenService, "tokenPeriod", 1000L);

        Token token = tokenService.generateToken("registrationId", "oAuthId", Role.ROLE_USER);
        Thread.sleep(1000L);

        boolean isExpired = tokenService.verifyToken(token.getToken());

        assertThat(isExpired).isFalse();

        ReflectionTestUtils.setField(tokenService, "tokenPeriod", tokenPeriod);
    }
}