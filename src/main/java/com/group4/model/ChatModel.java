package com.group4.model;

import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatModel {
    private Long chatID;
    private int customerID;
    private Date sentTime;
    private String contentMessage;
}
