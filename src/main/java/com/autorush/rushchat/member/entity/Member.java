package com.autorush.rushchat.member.entity;

import com.autorush.rushchat.domain.BaseTimeEntity;
import com.autorush.rushchat.member.type.Role;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.Builder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue
    private Long id;

    // info
    private String registeredPlatform; // key
    private String oAuthId; // key
    private String name;
    private String nickname;
    private String email;
    private String profileImage;
    @Enumerated(EnumType.STRING)
    private Role role;

    // sleeper
    private boolean sleeperUserYn;
    private Date sleeperDate;

    // auth
    private Long loginAttemptCount;
    private Date lastLoginDate;
    // createdAt
    // modifiedAt

    @Builder
    public Member(String registeredPlatform, String oAuthId, String name, String nickname,
        String email,
        String profileImage, Role role) {
        this.registeredPlatform = registeredPlatform;
        this.oAuthId = oAuthId;
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.profileImage = profileImage;
        this.role = role;
    }

    public Member update(String name, String email) {
        this.name = name;
        this.email = email;

        return this;
    }
}
