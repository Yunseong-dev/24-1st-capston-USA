package com.capstone.usa.chat.service;

import com.capstone.usa.chat.dto.MessageDto;
import com.capstone.usa.chat.model.JobChatMessage;
import com.capstone.usa.chat.model.JobChatRoom;
import com.capstone.usa.chat.repository.JobChatMessageRepository;
import com.capstone.usa.chat.repository.JobChatRoomRepository;
import com.capstone.usa.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JobChatService {

    private final JobChatRoomRepository jobChatRoomRepository;
    private final JobChatMessageRepository jobChatMessageRepository;

    @Transactional
    public void saveMessage(User user, MessageDto dto) {
        Optional<JobChatRoom> chatRoomOptional = jobChatRoomRepository.findByRoomId(dto.getChatRoomId());
        JobChatRoom chatRoom = chatRoomOptional.get();

        JobChatMessage chat = new JobChatMessage(
                0L,
                chatRoom,
                user.getName(),
                dto.getMessage(),
                LocalDateTime.now()
        );
        jobChatMessageRepository.save(chat);
    }
}
