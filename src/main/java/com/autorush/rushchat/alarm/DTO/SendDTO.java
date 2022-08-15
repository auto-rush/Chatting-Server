package com.autorush.rushchat.alarm.DTO;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class SendDTO {
    private BasicDTO notification;
    private String to;
}
