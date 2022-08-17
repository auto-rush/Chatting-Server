package com.autorush.rushchat.chat.repository;

import com.autorush.rushchat.chat.domain.Chat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SpringExtension.class)
@SpringBootTest
class ChatRepositoryTest {

    @Autowired
    MongoTemplate mongoTemplate;

    @Test
    @DisplayName("채팅방 번호에 맞는 채팅방의 모든 메세지 가져오기")
    @WithMockUser(roles = "USER", username = "test")
    void getAllMessageInRoom() {
        // given
        Long roomId = 1L; // => 채팅방 로직이 구성되면 존재하는 채팅방 번호로 가져올 예정

        Query query = new Query().addCriteria(Criteria.where("roomId").is(1));
        List<Chat> chatList = mongoTemplate.find(query, Chat.class);

//        List<Chat> chatList = chatRepository.findAll();

        for (int i = 0; i < chatList.size(); i++) {
            System.out.println(chatList.get(i));
        }

        // then
        assertThat(chatList.size()).isGreaterThan(0);
    }
}
