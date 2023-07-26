package com.friendfinder.friendfinderrest.endpoint;

import com.friendfinder.friendfindercommon.dto.chat.SendMessageDto;
import com.friendfinder.friendfindercommon.dto.chat.SentMessageResponseDto;
import com.friendfinder.friendfindercommon.entity.Chat;
import com.friendfinder.friendfindercommon.entity.Message;
import com.friendfinder.friendfindercommon.entity.User;
import com.friendfinder.friendfindercommon.security.CurrentUser;
import com.friendfinder.friendfindercommon.service.ChatService;
import com.friendfinder.friendfindercommon.service.MessageService;
import com.friendfinder.friendfindercommon.service.UserService;
import com.friendfinder.friendfindercommon.dto.chat.ChatDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatEndpoint {

    private final UserService userService;
    private final ChatService chatService;
    private final MessageService messageService;

    @GetMapping("/create/{id}")
    public ResponseEntity<ChatDto> createNewChat(@PathVariable("id") int userId, @AuthenticationPrincipal CurrentUser currentUser){
        boolean create = chatService.create(userId, currentUser.getUser());
        if(!create) return ResponseEntity.status(HttpStatus.CONFLICT).build();
        return ResponseEntity.ok(ChatDto.builder()
                .currentUserId(currentUser.getUser().getId())
                .anotherUserId(userId)
                .build());
    }

    @PostMapping("/send-message")
    public ResponseEntity<SentMessageResponseDto> sendMessage(@RequestBody SendMessageDto sendMessageDto,
                                                              @AuthenticationPrincipal CurrentUser currentUser){
        Optional<User> userById = userService.findUserById(sendMessageDto.getReceiverId());
        if (userById.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Optional<Chat> chatById = chatService.findById(sendMessageDto.getChatId());
        if(chatById.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        messageService.save(Message.builder()
                .receiver(userById.get())
                .chat(chatById.get())
                .sender(currentUser.getUser())
                .content(sendMessageDto.getContent())
                .sentAt(LocalDateTime.now())
                .build());

        return ResponseEntity.ok(SentMessageResponseDto.builder()
                        .receiverId(sendMessageDto.getReceiverId())
                        .senderId(currentUser.getUser().getId())
                        .content(sendMessageDto.getContent())
                .build());
    }

}
