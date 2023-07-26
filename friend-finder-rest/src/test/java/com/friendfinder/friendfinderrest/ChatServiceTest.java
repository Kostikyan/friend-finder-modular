package com.friendfinder.friendfinderrest;

import com.friendfinder.friendfindercommon.repository.ChatRepository;
import com.friendfinder.friendfindercommon.service.ChatService;
import com.friendfinder.friendfindercommon.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ChatServiceTest {

    @Mock
    private ChatRepository chatRepository;

    private ChatService sut;
    // service under test


    @Test
    void saveChat(){
        // aaa
        // arange - miam xalasto chat ksarqes Chat chat = new Chat
        // when(repo.save(any())).thenReturn(chat)
        // act_ sut,createChat(chat)
        // assert

    }


}