package com.capstone.usa.chat.controller;

import com.capstone.usa.chat.service.ChatRoomService;
import com.capstone.usa.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @PostMapping("/create/{jobId}")
    public ResponseEntity<?> createChatRoom(
            @PathVariable int jobId,
            @AuthenticationPrincipal User currentUser) {
        return chatRoomService.createChatRoom(jobId, currentUser);
    }
}
