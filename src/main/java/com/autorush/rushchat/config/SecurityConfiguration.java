package com.autorush.rushchat.config;

import static org.springframework.security.config.Customizer.withDefaults;

import com.autorush.rushchat.auth.filter.JwtFilter;
import com.autorush.rushchat.auth.handler.OAuthSuccessHandler;
import com.autorush.rushchat.auth.service.OAuthUserService;
import com.autorush.rushchat.auth.service.TokenService;
import com.autorush.rushchat.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final OAuthUserService oAuthUserService;
    private final OAuthSuccessHandler oAuthSuccessHandler;
    private final TokenService tokenService;
    private final MemberRepository memberRepository;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // @formatter:off
        http.csrf().disable(); //POST method is available
        http
                .authorizeRequests(a -> a
                        .antMatchers("/", "/error", "/webjars/**").permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling(e -> e
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                )
                .sessionManagement(s -> s
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .logout(l -> l
                        .clearAuthentication(true)
                        .logoutSuccessUrl("/").permitAll()
                )
                .csrf(c -> c
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                )
                .httpBasic(withDefaults())
                .oauth2Login(o -> o
                        .successHandler(oAuthSuccessHandler)
                        .userInfoEndpoint()
                        .userService(oAuthUserService)
                );
        // @formatter:on

        http.addFilterBefore(new JwtFilter(tokenService, memberRepository), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}