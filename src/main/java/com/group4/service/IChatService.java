package com.group4.service;

import com.group4.entity.ChatEntity;
import com.group4.entity.UserEntity;

import java.util.List;

public interface IChatService {
    public List<ChatEntity> findAll();
    public ChatEntity save(ChatEntity chatEntity);
    public List<UserEntity> findDistinctSendersByReceiverId();
}
