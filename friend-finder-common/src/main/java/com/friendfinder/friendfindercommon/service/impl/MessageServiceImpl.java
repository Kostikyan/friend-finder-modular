package com.friendfinder.friendfindercommon.service.impl;

import com.friendfinder.friendfindercommon.dto.chatDto.SendMessageDto;
import com.friendfinder.friendfindercommon.entity.Chat;
import com.friendfinder.friendfindercommon.entity.Message;
import com.friendfinder.friendfindercommon.entity.User;
import com.friendfinder.friendfindercommon.service.ChatService;
import com.friendfinder.friendfindercommon.service.MessageService;
import com.friendfinder.friendfindercommon.repository.MessageRepository;
import com.friendfinder.friendfindercommon.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final UserService userService;
    private final ChatService chatService;

    @Override
    public boolean save(SendMessageDto sendMessageDto, User currentUser) {
        Optional<User> userById = userService.findUserById(sendMessageDto.getReceiverId());
        if (userById.isEmpty()) {
            return false;
        }

        Optional<Chat> chatById = chatService.findById(sendMessageDto.getChatId());
        if(chatById.isEmpty()){
            return false;
        }

        messageRepository.save(Message.builder()
                .receiver(userById.get())
                .chat(chatById.get())
                .sender(currentUser)
                .content(sendMessageDto.getContent())
                .sentAt(LocalDateTime.now())
                .build());
        return true;
    }

    @Override
    public void save(Message message) {
        messageRepository.save(message);
    }
}
