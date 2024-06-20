package com.capstone.usa.chat.model;

import com.capstone.usa.article.model.Article;
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
    private String chatType;
    private Long referenceId;
    @ManyToOne
    private User user1;
    @ManyToOne
    private User user2;
    @Column(unique = true)
    private String roomId;
    private LocalDateTime createdAt;
}
