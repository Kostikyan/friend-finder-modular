package com.friendfinder.friendfinderweb.service.impl;

import com.friendfinder.friendfinderweb.entity.Message;
import com.friendfinder.friendfinderweb.repository.MessageRepository;
import com.friendfinder.friendfinderweb.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;

    @Override
    public void save(Message message) {
        messageRepository.save(message);
    }
}
