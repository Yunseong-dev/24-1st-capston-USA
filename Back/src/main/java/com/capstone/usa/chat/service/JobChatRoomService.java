package com.capstone.usa.chat.service;

import com.capstone.usa.chat.dto.ChatRoomIdDto;
import com.capstone.usa.chat.dto.UserNameDto;
import com.capstone.usa.chat.model.JobChatMessage;
import com.capstone.usa.chat.model.JobChatRoom;
import com.capstone.usa.chat.repository.JobChatMessageRepository;
import com.capstone.usa.chat.repository.JobChatRoomRepository;
import com.capstone.usa.job.model.Job;
import com.capstone.usa.job.service.JobService;
import com.capstone.usa.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobChatRoomService {

    private final JobChatRoomRepository jobchatRoomRepository;
    private final JobChatMessageRepository jobChatMessageRepository;
    private final JobService jobService;

    @Transactional
    public ResponseEntity<?> createChatRoom(int jobId, User user) {
        Job job = jobService.findJobById(jobId);
        if (job == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("해당 게시물을 찾을 수 없습니다");
        }

        User postOwner = job.getUser();

        if (user.getId().equals(job.getUser().getId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("자신이 작성한 게시물에 대한 채팅은 할 수 없습니다");
        }

        Optional<JobChatRoom> optionalChatRoom = jobchatRoomRepository.findByJobAndUser1AndUser2(job, postOwner, user);
        JobChatRoom chatRoom;
        if (optionalChatRoom.isPresent()) {
            chatRoom = optionalChatRoom.get();
        } else {
            chatRoom = new JobChatRoom(
                    0L,
                    job,
                    postOwner,
                    user,
                    UUID.randomUUID().toString(),
                    LocalDateTime.now()
            );
            jobchatRoomRepository.save(chatRoom);
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ChatRoomIdDto(chatRoom.getRoomId(), ""));
    }

    @Transactional(readOnly = true)
    public List<JobChatMessage> getChatIncludeChatId(String roomId) {
        Optional<JobChatRoom> optionalChatRoom = jobchatRoomRepository.findByRoomId(roomId);
        JobChatRoom chatRoom = optionalChatRoom.get();

        return jobChatMessageRepository.findByChatRoomId(chatRoom.getId());
    }

    @Transactional(readOnly = true)
    public List<ChatRoomIdDto> getChatRoomsForUser(User user) {
        List<JobChatRoom> chatRooms = jobchatRoomRepository.findByUser1OrUser2(user, user);
        return chatRooms.stream()
                .map(chatRoom -> new ChatRoomIdDto(chatRoom.getRoomId(), chatRoom.getJob().getTitle()))
                .collect(Collectors.toList());
    }

    public UserNameDto getUserName(User user) {
        return new UserNameDto(user.getName());
    }
}
