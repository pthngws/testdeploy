package com.group4.service;

import com.group4.entity.ChatEntity;
import com.group4.entity.UserEntity;

import java.util.List;
import java.util.Map;

public interface IChatService {
    List<Map<String, Object>> findDistinctSendersByReceiverId();;

    ChatEntity save(ChatEntity chatEntity);

    List<ChatEntity> findAll();
}
