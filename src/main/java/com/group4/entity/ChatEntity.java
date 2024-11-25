package com.group4.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "chats")
public class ChatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_id")
    private Long chatID;

    @Column(name = "sender_id", nullable = false)
    private Long senderID;

    @Column(name = "receiver_id", nullable = false)
    private Long receiverID;

    @Column(name = "sentTime", nullable = false)
    private Date sentTime;

    @Column(name = "content",columnDefinition = "MEDIUMTEXT", nullable = false)
    private String contentMessage;
}
