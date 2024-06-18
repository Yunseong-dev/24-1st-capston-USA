package com.capstone.usa.chat.controller;

import com.capstone.usa.chat.dto.ChatRoomIdDto;
import com.capstone.usa.chat.model.ChatRoom;
import com.capstone.usa.chat.service.ChatRoomService;
import com.capstone.usa.user.model.User;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/chat")
@AllArgsConstructor
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @PostMapping("/create")
    public ResponseEntity<?> createChatRoom(
            @RequestParam int jobId,
            @AuthenticationPrincipal User currentUser
    ) {
        try {
            ChatRoomIdDto dto = chatRoomService.createChatRoom(jobId, currentUser);
            return ResponseEntity.status(HttpStatus.CREATED).body(dto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("채팅방 생성에 실패했습니다");
        }
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<?> getChatRoom(@PathVariable String roomId) {
        try {
            Optional<ChatRoom> chatRoom = chatRoomService.getChatRoomById(roomId);
            return chatRoom.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("해당 채팅방을 찾을 수 없습니다");
        }
    }
}
