package com.autorush.rushchat.room.repository;

import com.autorush.rushchat.room.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
