package com.autorush.rushchat.auth.service;

import com.autorush.rushchat.auth.dto.OAuthUserDto;
import com.autorush.rushchat.auth.type.OAuthAttributes;
import com.autorush.rushchat.member.entity.Member;
import com.autorush.rushchat.member.repository.MemberRepository;
import com.autorush.rushchat.member.type.Role;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuthUserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        // 제공 서비스 별 키 값으로 사용되는 attribute 의 이름
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
        Map<String, Object> attributes = new HashMap<>(oAuth2User.getAttributes());
        attributes.put("registrationId", registrationId); // 계속 필요한 정보라 담아버림

        // 회원 가입에 사용할 데이터 추출
        OAuthUserDto userDto = OAuthAttributes.extract(registrationId, attributes);
        Map<String, Object> memberAttributes = userDto.toMap();
        Member member = saveOrUpdate(userDto);

        // 권한 부여
        return new DefaultOAuth2User(
            Collections.singleton(new SimpleGrantedAuthority(Role.ROLE_USER.name())),
            memberAttributes, // default OAuth2User.getAttributes 불필요하게 큰 데이터라서 일부만 송신
            userNameAttributeName
        );
    }


    private Member saveOrUpdate(OAuthUserDto userDto) {
        Member member = memberRepository.findByRegistrationIdAndOAuthId(userDto.getRegisteredPlatform(), userDto.getOAuthId())
            .map(m -> m.update(userDto.getName(), userDto.getEmail())) // OAuth 서비스에서 유저 정보 변경 내역 적용
            .orElse(userDto.toMember());
        return memberRepository.save(member);
    }

}
