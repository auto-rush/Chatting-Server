package com.autorush.rushchat.domain.room.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CreateRoomCommand {
    private String roomName;
    private String ownerId;
    private Long maxParticipants;
}
