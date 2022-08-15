package com.autorush.rushchat.member.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class MemberPK implements Serializable {

    @Id
    @Column(name = "registered_platform")
    private String registeredPlatform;
    @Id
    @Column(name = "oauth_id")
    private String oauthId;

    public String getRegisteredPlatform() {
        return registeredPlatform;
    }

    public void setRegisteredPlatform(String registeredPlatform) {
        this.registeredPlatform = registeredPlatform;
    }

    public String getOauthId() {
        return oauthId;
    }

    public void setOauthId(String oauthId) {
        this.oauthId = oauthId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MemberPK that = (MemberPK) o;
        return Objects.equals(registeredPlatform, that.registeredPlatform) && Objects.equals(oauthId, that.oauthId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(registeredPlatform, oauthId);
    }
}
