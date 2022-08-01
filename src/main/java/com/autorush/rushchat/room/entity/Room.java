package com.autorush.rushchat.room.entity;

import com.autorush.rushchat.common.BaseTimeEntity;
import com.autorush.rushchat.member.entity.Member;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Room extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String roomName;
    private String ownerOAuthId;
    private String ownerRegisteredPlatform;
    private Long participants;
    private Long maxParticipants;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="rm_room_id")
    private List<RoomMember> roomMembers;

    @Builder
    public Room(String roomName, String ownerOAuthId, String ownerRegisteredPlatform, Long maxParticipants, Member member) {
        this.roomName = roomName;
        this.ownerOAuthId = ownerOAuthId;
        this.ownerRegisteredPlatform = ownerRegisteredPlatform;
        this.participants = 1L;
        this.maxParticipants = maxParticipants != null ? maxParticipants : 30L;
        this.roomMembers = new ArrayList<>();

        RoomMember roomMember = new RoomMember(this.id, member);
        this.roomMembers.add(roomMember);
    }
}

