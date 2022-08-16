package com.autorush.rushchat.member.entity;

import com.autorush.rushchat.domain.BaseTimeEntity;
import com.autorush.rushchat.member.type.Role;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GenerationType;
import javax.persistence.IdClass;
import javax.persistence.Table;
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
@IdClass(MemberPK.class)
//@Table(name = "member", schema = "rushchat")
public class Member extends BaseTimeEntity {
    // info
    @Id
    @Column(name = "registered_platform")
    private String registeredPlatform;

    @Id
    @Column(name = "oauth_id")
    private String oauthId;
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
    public Member(String registeredPlatform, String oauthId, String name, String nickname,
        String email,
        String profileImage, Role role) {
        this.registeredPlatform = registeredPlatform;
        this.oauthId = oauthId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Member that = (Member) o;
        return Objects.equals(registeredPlatform, that.registeredPlatform) && Objects.equals(oauthId, that.oauthId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(registeredPlatform, oauthId);
    }
}
