package com.capstone.usa.job.repository;

import com.capstone.usa.job.model.JobChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobChatMessageRepository extends JpaRepository<JobChatMessage, Long> {
    List<JobChatMessage> findByChatRoomId(Long chatRoomId);
}
