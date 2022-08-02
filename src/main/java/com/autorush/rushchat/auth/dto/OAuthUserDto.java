package com.autorush.rushchat.auth.dto;

import com.autorush.rushchat.member.entity.Member;
import com.autorush.rushchat.member.type.Role;
import java.util.HashMap;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OAuthUserDto {
    private final String registeredPlatform;
    private final String oAuthId;
    private final String name;
    private final String nickname;
    private final String email;
    private final String profileImage;

    @Builder
    public OAuthUserDto(String registeredPlatform, String oAuthId, String name, String nickname, String email, String profileImage) {
        this.registeredPlatform = registeredPlatform;
        this.oAuthId = oAuthId;
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.profileImage = profileImage;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("registrationId", registeredPlatform);
        map.put("id", oAuthId);
        map.put("name", name);
        map.put("nickname", nickname);
        map.put("email", email);
        map.put("image", profileImage);
        return map;
    }

    public Member toMember() {
        return Member.builder()
            .registeredPlatform(registeredPlatform)
            .oAuthId(oAuthId)
            .name(name)
            .nickname(nickname)
            .email(email)
            .profileImage(profileImage)
            .role(Role.ROLE_USER)
            .build();
    }
}