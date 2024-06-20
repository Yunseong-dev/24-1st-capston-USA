package com.capstone.usa.chat.controller;

import com.capstone.usa.chat.dto.ChatRoomIdDto;
import com.capstone.usa.chat.dto.UserNameDto;
import com.capstone.usa.chat.dto.MessageDto;
import com.capstone.usa.chat.model.JobChatMessage;
import com.capstone.usa.chat.service.JobChatRoomService;
import com.capstone.usa.chat.service.JobChatService;
import com.capstone.usa.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/job/chat")
@RequiredArgsConstructor
public class JobChatController {

    private final JobChatRoomService jobchatRoomService;
    private final JobChatService jobchatService;

    @PostMapping("/create/{jobId}")
    public ResponseEntity<?> createChatRoom(
            @PathVariable int jobId,
            @AuthenticationPrincipal User user
    ) {
        return jobchatRoomService.createChatRoom(jobId, user);
    }

    @PostMapping("/sendMessage")
    public void sendMessage(
            @RequestBody MessageDto dto,
            @AuthenticationPrincipal User user
    ) {
        jobchatService.saveMessage(user, dto);
    }

    @GetMapping("/messages/{roomId}")
    public ResponseEntity<List<JobChatMessage>> getChatMessages(
            @PathVariable String roomId
    ) {
        List<JobChatMessage> messages = jobchatRoomService.getChatIncludeChatId(roomId);
        return ResponseEntity.status(HttpStatus.OK).body(messages);
    }

    @GetMapping("/getUser")
    public UserNameDto getUserName(
            @AuthenticationPrincipal User user
    ) {
        return jobchatRoomService.getUserName(user);
    }

    @GetMapping("/myRooms")
    public ResponseEntity<List<ChatRoomIdDto>> getMyChatRooms(
            @AuthenticationPrincipal User currentUser
    ) {
        List<ChatRoomIdDto> chatRooms = jobchatRoomService.getChatRoomsForUser(currentUser);
        return ResponseEntity.status(HttpStatus.OK).body(chatRooms);
    }

}
