package com.autorush.rushchat.member.repository;

import com.autorush.rushchat.member.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM member m WHERE m.registered_platform = ?1 AND m.o_auth_id = ?2")
    Optional<Member> findByRegistrationIdAndOAuthId(String registeredPlatform, String oAuthId);
}