package com.capstone.usa.chat.model;

import com.capstone.usa.jobpost.model.Job;
import com.capstone.usa.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "chat_room")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Job job;

    @ManyToOne
    private User user1; // 첫 번째 사용자 (게시물 작성자)

    @ManyToOne
    private User user2; // 두 번째 사용자 (현재 로그인한 사용자)

    @Column(unique = true)
    private String roomId; // 채팅방 식별자 (UUID 형식)

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
