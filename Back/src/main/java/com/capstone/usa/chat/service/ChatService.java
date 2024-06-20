package com.capstone.usa.chat.service;

import com.capstone.usa.chat.dto.MessageDto;
import com.capstone.usa.chat.model.Chat;
import com.capstone.usa.chat.model.ChatRoom;
import com.capstone.usa.chat.repository.ChatRepository;
import com.capstone.usa.chat.repository.ChatRoomRepository;
import com.capstone.usa.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatRepository chatRepository;

    @Transactional
    public void saveMessage(User user, MessageDto dto) {
        Optional<ChatRoom> chatRoomOptional = chatRoomRepository.findByRoomId(dto.getChatRoomId());
        ChatRoom chatRoom = chatRoomOptional.get();

        Chat chat = new Chat(
                0L,
                chatRoom,
                user.getName(),
                dto.getMessage(),
                LocalDateTime.now()
        );

        chatRepository.save(chat);
    }
}
