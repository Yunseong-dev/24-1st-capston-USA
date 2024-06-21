package com.capstone.usa.chat.repository;

import com.capstone.usa.chat.model.ChatRoom;
import com.capstone.usa.auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    Optional<ChatRoom> findByChatTypeAndReferenceIdAndUser1AndUser2(String chatType, Long referenceId, User user1, User user2);

    Optional<ChatRoom> findByRoomId(String roomId);

    List<ChatRoom> findByUser1OrUser2(User user1, User user2);
}
