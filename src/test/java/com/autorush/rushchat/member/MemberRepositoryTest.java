package com.autorush.rushchat.member;

import static org.assertj.core.api.Assertions.assertThat;

import com.autorush.rushchat.member.entity.Member;
import com.autorush.rushchat.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class MemberRepositoryTest {
    @Autowired
    DataSource dataSource;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("슬라이싱 테스트 의존성 주입 확인")
    public void di() {
        // DI test
    }

    @Test
    @DisplayName("삽입 및 가져오기 테스트")
    public void insertAndGetTest() {
        String name = "asdf";
        String registeredPlatform = "registeredPlatform";
        String oauthId = "oauthId";
        final Member member = Member.builder()
            .registeredPlatform(registeredPlatform)
            .oauthId(oauthId)
            .name(name)
            .build();
        memberRepository.save(member);
        Member byId = memberRepository.findByRegisteredPlatformAndOauthId(registeredPlatform, oauthId).orElse(new Member());
        assertThat(byId.getName()).isEqualTo(name);
    }

}