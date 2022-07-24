package com.autorush.rushchat.alarm.DTO;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class SendToGroupDTO {
    private String topic;
    private String data;
}
