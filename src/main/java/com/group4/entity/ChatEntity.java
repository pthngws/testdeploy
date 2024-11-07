package com.group4.entity;

import jakarta.persistence.*;
import lombok.*;

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

    @Column(name = "customer_id", nullable = false)
    private int customerID;

    @Column(name = "type_of_message", nullable = false)
    private int typeOfMessage;

    @Column(name = "content",columnDefinition = "MEDIUMTEXT", nullable = false)
    private String contentMessage;
}
