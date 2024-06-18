package com.capstone.usa.chat.service;

import com.capstone.usa.chat.dto.ChatRoomIdDto;
import com.capstone.usa.chat.model.Chat;
import com.capstone.usa.chat.model.ChatRoom;
import com.capstone.usa.chat.repository.ChatRoomRepository;
import com.capstone.usa.jobpost.model.Job;
import com.capstone.usa.jobpost.service.JobService;
import com.capstone.usa.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final JobService jobService;
    private final ChatService chatService;

    @Transactional
    public ResponseEntity<?> createChatRoom(int jobId, User currentUser) {
        Job job = jobService.findJobById(jobId);
        if (job == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("해당 게시물을 찾을 수 없습니다");
        }

        User postOwner = job.getUser();

        if (currentUser.getId().equals(job.getUser().getId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("자신이 작성한 게시물에 대한 채팅은 할 수 없습니다");
        }

        Optional<ChatRoom> existingChatRoom = chatRoomRepository.findByJobIdAndUser1PhoneNumberAndUser2PhoneNumber(jobId, postOwner.getId(), currentUser.getId());
        if (existingChatRoom.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("해당 게시물을 찾을 수 없습니다");
        }

        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setJob(job);
        chatRoom.setUser1(postOwner);
        chatRoom.setUser2(currentUser);
        chatRoom.setRoomId(UUID.randomUUID().toString());

        chatRoomRepository.save(chatRoom);

        return ResponseEntity.status(HttpStatus.OK).body(new ChatRoomIdDto(chatRoom.getRoomId()));
    }

    @Transactional(readOnly = true)
    public Optional<ChatRoom> getChatRoomById(String roomId) {
        return chatRoomRepository.findByRoomId(roomId);
    }

    @Transactional(readOnly = true)
    public List<Chat> getMessagesByChatRoom(ChatRoom chatRoom) {
        return chatService.getMessagesByChatRoom(chatRoom);
    }

    @Transactional
    public void saveMessage(ChatRoom chatRoom, User sender, String message) {
        chatService.saveMessage(chatRoom, sender, message);
    }
}
