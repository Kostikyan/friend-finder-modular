package com.friendfinder.friendfinderrest.endpoint;

import com.friendfinder.friendfindercommon.dto.chatDto.SendMessageDto;
import com.friendfinder.friendfindercommon.dto.chatDto.SentMessageResponseDto;
import com.friendfinder.friendfindercommon.security.CurrentUser;
import com.friendfinder.friendfindercommon.service.ChatService;
import com.friendfinder.friendfindercommon.service.MessageService;
import com.friendfinder.friendfindercommon.dto.chatDto.ChatDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatEndpoint {

    private final ChatService chatService;
    private final MessageService messageService;

    @GetMapping("/create/{id}")
    public ResponseEntity<ChatDto> createNewChat(@PathVariable("id") int userId, @AuthenticationPrincipal CurrentUser currentUser) {
        boolean create = chatService.create(userId, currentUser.getUser());
        if (!create) return ResponseEntity.status(HttpStatus.CONFLICT).build();
        return ResponseEntity.ok(ChatDto.builder()
                .currentUserId(currentUser.getUser().getId())
                .anotherUserId(userId)
                .build());
    }

    @PostMapping("/send-message")
    public ResponseEntity<SentMessageResponseDto> sendMessage(@RequestBody SendMessageDto sendMessageDto,
                                                              @AuthenticationPrincipal CurrentUser currentUser) {
        boolean save = messageService.save(sendMessageDto, currentUser.getUser());

        return save ? ResponseEntity.ok(SentMessageResponseDto.builder()
                .receiverId(sendMessageDto.getReceiverId())
                .senderId(currentUser.getUser().getId())
                .content(sendMessageDto.getContent())
                .build()) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
