package com.capstone.usa.chat.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChatRoomIdDto {
    private String roomId;

    public ChatRoomIdDto(String roomId) {
        this.roomId = roomId;
    }
}
