package com.autorush.rushchat.chat.entity;

import com.autorush.rushchat.common.BaseTimeEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Message extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String contents;
    Long roomId;
    Long sender;

    @Builder
    public Message(String contents, Long roomId, Long sender) {
        this.contents = contents;
        this.roomId = roomId;
        this.sender = sender;
    }
}
