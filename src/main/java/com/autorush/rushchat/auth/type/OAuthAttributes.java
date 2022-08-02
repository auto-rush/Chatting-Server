package com.autorush.rushchat.auth.type;

import com.autorush.rushchat.auth.dto.OAuthUserDto;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;

public enum OAuthAttributes {
    GITHUB("github", (attributes) -> OAuthUserDto.builder()
        .registeredPlatform((String) attributes.get("registrationId"))
        .oAuthId(String.valueOf(attributes.get("id"))) // Integer
        .name((String) attributes.get("name"))
        .nickname((String) attributes.get("login"))
        .email((String) attributes.get("email"))
        .profileImage((String) attributes.get("avatar_url"))
        .build());

    private final String registeredPlatform;
    private final Function<Map<String, Object>, OAuthUserDto> of;

    OAuthAttributes(String registeredPlatform, Function<Map<String, Object>, OAuthUserDto> of) {
        this.registeredPlatform = registeredPlatform;
        this.of = of;
    }

    public static OAuthUserDto extract(String registrationId, Map<String, Object> attributes) {
        return Arrays.stream(values())
            .filter(provider -> registrationId.equals(provider.registeredPlatform))
            .findFirst()
            .orElseThrow(IllegalArgumentException::new) // 없으면 에러를 발생시킨다.
            .of.apply(attributes);
    }
}
