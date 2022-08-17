package com.autorush.rushchat.chat.domain;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Document("chat")
@Getter @Setter
@NoArgsConstructor
public class Chat {

    @Id
    private String _id;

    private Long roomId;

    private Long chatId;

    private String content;

}