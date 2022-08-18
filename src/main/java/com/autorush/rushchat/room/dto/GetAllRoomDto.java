package com.autorush.rushchat.room.dto;

import com.autorush.rushchat.room.entity.Room;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class GetAllRoomDto {
    private Long id;
    private String roomName;
    private String ownerOAuthId;
    private String ownerRegisteredPlatform;
    private Long participants;
    private Long maxParticipants;
    private LocalDateTime modifiedAt;

    public GetAllRoomDto(Room room) {
        this.id = room.getId();
        this.roomName = room.getRoomName();
        this.ownerOAuthId = room.getOwnerOAuthId();
        this.ownerRegisteredPlatform = room.getOwnerRegisteredPlatform();
        this.participants = room.getParticipants();
        this.maxParticipants = room.getMaxParticipants();
        this.modifiedAt = room.getModifiedAt();
    }

}
