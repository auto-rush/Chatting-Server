package com.autorush.rushchat.room.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class InOutRoomDto {
    private Long roomId;
    private String ownerOAuthId;
    private String ownerRegisteredPlatform;
}
