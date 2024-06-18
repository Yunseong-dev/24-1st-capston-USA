package com.capstone.usa.chat.repository;

import com.capstone.usa.chat.model.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    Optional<ChatRoom> findByRoomId(String roomId);

    Optional<ChatRoom> findByJobIdAndUser1PhoneNumberAndUser2PhoneNumber(int jobId, String user1Id, String user2Id);
}

