package com.autorush.rushchat.chat.repository;

import com.autorush.rushchat.chat.entity.Chat;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends MongoRepository<Chat,String> {
    List<Chat> findByRoomId(Long roomId);

    List<Chat> findAll();
}
