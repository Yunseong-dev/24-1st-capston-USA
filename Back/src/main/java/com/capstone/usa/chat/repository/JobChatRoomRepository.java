package com.capstone.usa.chat.repository;

import com.capstone.usa.chat.model.JobChatRoom;
import com.capstone.usa.job.model.Job;
import com.capstone.usa.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobChatRoomRepository extends JpaRepository<JobChatRoom, Long> {
    Optional<JobChatRoom> findByRoomId(String roomId);

    Optional<JobChatRoom> findByJobAndUser1AndUser2(Job job, User user1, User user2);

    List<JobChatRoom> findByUser1OrUser2(User user1, User user2);
}

