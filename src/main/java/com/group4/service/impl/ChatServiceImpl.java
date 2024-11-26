package com.group4.service.impl;

import com.group4.entity.ChatEntity;
import com.group4.entity.UserEntity;
import com.group4.repository.ChatRepository;
import com.group4.service.IChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatServiceImpl implements IChatService {

    @Autowired
    private ChatRepository chatRepository;

    @Override
    public List<UserEntity> findDistinctSendersByReceiverId() {
        return chatRepository.findDistinctSendersByReceiverId();
    }

    public ChatEntity save(ChatEntity chatEntity) {
        return chatRepository.save(chatEntity);
    }

    public List<ChatEntity> findAll() {
        return chatRepository.findAll();
    }
}
