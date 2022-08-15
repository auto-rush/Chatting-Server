package com.autorush.rushchat.room.repository;

import com.autorush.rushchat.member.entity.Member;
import com.autorush.rushchat.room.entity.RoomMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomMemberRepository extends JpaRepository<RoomMember, Long> {
    Optional<RoomMember> findByRoomIdAndMember(Long roomId, Member member);
}
