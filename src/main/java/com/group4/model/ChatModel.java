package com.group4.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatModel {
    private Long chatID;
    private int customerID;
    private int typeOfMessage;
    private String contentMessage;
}
