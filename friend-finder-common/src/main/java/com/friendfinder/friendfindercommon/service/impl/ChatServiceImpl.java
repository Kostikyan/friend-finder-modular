package com.friendfinder.friendfindercommon.service.impl;

import com.friendfinder.friendfindercommon.entity.Chat;
import com.friendfinder.friendfindercommon.repository.ChatRepository;
import com.friendfinder.friendfindercommon.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
    private final ChatRepository chatRepository;

    @Override
    public List<Chat> findAllByCurrentUserId(int currentUserId) {
        return chatRepository.findAllByCurrentUserId(currentUserId);
    }

    @Override
    public List<Chat> findAllByAnotherUserId(int anotherUserId) {
        return chatRepository.findAllByAnotherUserId(anotherUserId);
    }

    @Override
    public Optional<Chat> findByCurrentUserIdAndAnotherUserId(int firstId, int secondId) {
        return chatRepository.findByCurrentUserIdAndAnotherUserId(firstId, secondId);
    }

    @Override
    public Optional<Chat> findById(int id) {
        return chatRepository.findById(id);
    }

    @Override
    public void save(Chat chat) {
        chatRepository.save(chat);
    }
}

