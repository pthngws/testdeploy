package com.group4.service;

import com.group4.entity.ChatEntity;
import com.group4.entity.UserEntity;

import java.util.List;

public interface IChatService {
    List<UserEntity> findDistinctSendersByReceiverId();
    List<Long> findDistinctGuestsByReceiverId();

    ChatEntity save(ChatEntity chatEntity);

    List<ChatEntity> findAll();
}
