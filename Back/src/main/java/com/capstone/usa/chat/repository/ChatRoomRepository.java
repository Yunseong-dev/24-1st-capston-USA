package com.capstone.usa.chat.repository;

import com.capstone.usa.chat.model.ChatRoom;
import com.capstone.usa.jobpost.model.Job;
import com.capstone.usa.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    Optional<ChatRoom> findByRoomId(String roomId);

    Optional<ChatRoom> findByJobAndUser1AndUser2(Job job, User user1, User user2);

    List<ChatRoom> findByUser1OrUser2(User user1, User user2);
}

