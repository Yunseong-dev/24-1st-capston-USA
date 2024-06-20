package com.capstone.usa.chat.service;

import com.capstone.usa.chat.dto.MessageDto;
import com.capstone.usa.chat.model.JobChatMessage;
import com.capstone.usa.chat.model.JobChatRoom;
import com.capstone.usa.chat.repository.JobChatRepository;
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

    private final JobChatRoomRepository chatRoomRepository;
    private final JobChatRepository chatRepository;

    @Transactional
    public void saveMessage(User user, MessageDto dto) {
        Optional<JobChatRoom> chatRoomOptional = chatRoomRepository.findByRoomId(dto.getChatRoomId());
        JobChatRoom chatRoom = chatRoomOptional.get();

        JobChatMessage chat = new JobChatMessage(
                0L,
                chatRoom,
                user.getName(),
                dto.getMessage(),
                LocalDateTime.now()
        );
        chatRepository.save(chat);
    }
}
