package com.autorush.rushchat.auth.filter;

import com.autorush.rushchat.auth.dto.JwtSubject;
import com.autorush.rushchat.auth.service.TokenService;
import com.autorush.rushchat.member.repository.MemberRepository;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

@RequiredArgsConstructor
public class JwtFilter extends GenericFilterBean {
    private final TokenService tokenService;
    private final MemberRepository memberRepository;

    @Override
    public void doFilter(
        ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = null;

        Cookie[] cookies = ((HttpServletRequest) request).getCookies();
        if (cookies != null) {
            // jwt 라는 이름으로 담긴 쿠키를 찾는다.
            token = Arrays.stream(cookies).filter(c -> c.getName().equals("jwt")).findFirst().map(Cookie::getValue).orElse(null);
        }

        if (token != null && tokenService.verifyToken(token)) {
            JwtSubject jwtSubject = new JwtSubject(tokenService.getClaimsSubject(token));
            // 회원가입이 되어있는 사용자인 경우에만 인증
            memberRepository.findByRegistrationIdAndOAuthId(jwtSubject.getRegistrationId(), jwtSubject.getOAuthId())
                .ifPresent(m -> {
                    Authentication authentication = new UsernamePasswordAuthenticationToken(m, "",
                        List.of(new SimpleGrantedAuthority(m.getRole().name())));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                });
        }

        chain.doFilter(request, response);
    }
}
