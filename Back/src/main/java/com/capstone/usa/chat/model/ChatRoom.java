package com.capstone.usa.chat.model;

import com.capstone.usa.job.model.Job;
import com.capstone.usa.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "job_chat")
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
    private User user1;
    @ManyToOne
    private User user2;
    @Column(unique = true)
    private String roomId;
    private LocalDateTime createdAt;
}
