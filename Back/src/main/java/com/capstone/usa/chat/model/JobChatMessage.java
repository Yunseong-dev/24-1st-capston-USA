package com.capstone.usa.chat.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "Job_Message")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private JobChatRoom JobchatRoom;
    private String sender;
    private String message;
    private LocalDateTime sendAt;

}
