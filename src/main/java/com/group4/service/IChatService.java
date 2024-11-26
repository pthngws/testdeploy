package com.group4.service;

import com.group4.entity.ChatEntity;
import com.group4.entity.UserEntity;

import java.util.List;

public interface IChatService {
    public ChatEntity save(ChatEntity chatEntity);
    public List<ChatEntity> findAll();
    List<UserEntity> findDistinctSendersByReceiverId();
    List<Long> findDistinctGuestsByReceiverId();
}
