package com.capstone.usa.chat.controller;

import com.capstone.usa.chat.dto.ChatRoomIdDto;
import com.capstone.usa.chat.dto.UserNameDto;
import com.capstone.usa.chat.dto.MessageDto;
import com.capstone.usa.chat.model.Chat;
import com.capstone.usa.chat.service.ChatRoomService;
import com.capstone.usa.chat.service.ChatService;
import com.capstone.usa.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService chatRoomService;
    private final ChatService chatService;

    @PostMapping("/create/{jobId}")
    public ResponseEntity<?> createChatRoom(
            @PathVariable int jobId,
            @AuthenticationPrincipal User user
    ) {
        return chatRoomService.createChatRoom(jobId, user);
    }

    @PostMapping("/sendMessage")
    public void sendMessage(
            @RequestBody MessageDto dto,
            @AuthenticationPrincipal User user
    ) {
        chatService.saveMessage(user, dto);
    }

    @GetMapping("/messages/{roomId}")
    public ResponseEntity<List<Chat>> getChatMessages(
            @PathVariable String roomId
    ) {
        List<Chat> messages = chatRoomService.getChatIncludeChatId(roomId);
        return ResponseEntity.status(HttpStatus.OK).body(messages);
    }

    @GetMapping("/getUser")
    public UserNameDto getUserName(
            @AuthenticationPrincipal User user
    ) {
        return chatRoomService.getUserName(user);
    }

    @GetMapping("/myRooms")
    public ResponseEntity<List<ChatRoomIdDto>> getMyChatRooms(
            @AuthenticationPrincipal User currentUser
    ) {
        List<ChatRoomIdDto> chatRooms = chatRoomService.getChatRoomsForUser(currentUser);
        return ResponseEntity.status(HttpStatus.OK).body(chatRooms);
    }

}
