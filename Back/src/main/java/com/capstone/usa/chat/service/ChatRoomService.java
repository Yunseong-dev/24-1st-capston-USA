package com.capstone.usa.chat.service;

import com.capstone.usa.chat.dto.ChatRoomIdDto;
import com.capstone.usa.chat.model.ChatRoom;
import com.capstone.usa.chat.repository.ChatRoomRepository;
import com.capstone.usa.jobpost.model.Job;
import com.capstone.usa.jobpost.service.JobService;
import com.capstone.usa.user.model.User;
import com.capstone.usa.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final JobService jobService;
    private final UserService userService;

    @Transactional
    public ChatRoomIdDto createChatRoom(int jobId, User currentUser) {
        Job job = jobService.findJobById(jobId);
        if (job == null) {
            throw new IllegalArgumentException("Job not found");
        }

        User postOwner = job.getUser();

        Optional<ChatRoom> existingChatRoom = chatRoomRepository.findByJobIdAndUser1PhoneNumberAndUser2PhoneNumber(jobId, postOwner.getId(), currentUser.getId());
        if (existingChatRoom.isPresent()) {
            return new ChatRoomIdDto(existingChatRoom.get().getRoomId());
        }

        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setJob(job);
        chatRoom.setUser1(postOwner);
        chatRoom.setUser2(currentUser);
        chatRoom.setRoomId(UUID.randomUUID().toString());

        chatRoomRepository.save(chatRoom);

        return new ChatRoomIdDto(chatRoom.getRoomId());
    }


    @Transactional(readOnly = true)
    public Optional<ChatRoom> getChatRoomById(String roomId) {
        return chatRoomRepository.findByRoomId(roomId);
    }
}
