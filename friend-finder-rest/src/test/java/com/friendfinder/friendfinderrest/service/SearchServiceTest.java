package com.friendfinder.friendfinderrest.service;

import com.friendfinder.friendfindercommon.entity.Country;
import com.friendfinder.friendfindercommon.entity.User;
import com.friendfinder.friendfindercommon.entity.types.UserGender;
import com.friendfinder.friendfindercommon.entity.types.UserRole;
import com.friendfinder.friendfindercommon.repository.FriendRequestRepository;
import com.friendfinder.friendfindercommon.repository.UserRepository;
import com.friendfinder.friendfindercommon.security.CurrentUser;
import com.friendfinder.friendfindercommon.service.impl.FriendRequestServiceImpl;
import com.friendfinder.friendfindercommon.service.impl.MailService;
import com.friendfinder.friendfindercommon.service.impl.SearchServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SearchServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private SearchServiceImpl searchService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        searchService = new SearchServiceImpl(userRepository);
    }

    @Test
    void testSearchByKeyword_KeywordFound() {
        String keyword = "John";
        int currentPage = 1;
        CurrentUser currentUser = new CurrentUser(mockUser());

        User user1 = mockUser();
        user1.setName("John");
        User user2 = mockUser();

        Page<User> mockedPage = createMockedPage(Arrays.asList(user1, user2));

        when(userRepository.findByNameContainingIgnoreCaseOrSurnameContainingIgnoreCase(anyString(), anyString(), any(Pageable.class)))
                .thenReturn(mockedPage);

        Page<User> resultPage = searchService.searchByKeyword(keyword, currentUser, currentPage);


        assertNotNull(resultPage);
        assertEquals(2, resultPage.getTotalElements());
        assertEquals(user1, resultPage.getContent().get(0));
        assertEquals(user2, resultPage.getContent().get(1));

        verify(userRepository, times(1))
                .findByNameContainingIgnoreCaseOrSurnameContainingIgnoreCase(keyword, keyword, PageRequest.of(currentPage - 1, 2));
    }

    @Test
    void testSearchByKeyword_KeywordNotFound() {
        String keyword = "John";
        int currentPage = 1;
        CurrentUser currentUser = new CurrentUser(mockUser());

        List<User> emptyList = Arrays.asList();

        Page<User> mockedPage = createMockedPage(emptyList);

        when(userRepository.findByNameContainingIgnoreCaseOrSurnameContainingIgnoreCase(anyString(), anyString(), any(Pageable.class)))
                .thenReturn(mockedPage);

        Page<User> resultPage = searchService.searchByKeyword(keyword, currentUser, currentPage);

        assertNotNull(resultPage);
        assertEquals(0, resultPage.getTotalElements());
        assertEquals(emptyList, resultPage.getContent());

        verify(userRepository, times(1))
                .findByNameContainingIgnoreCaseOrSurnameContainingIgnoreCase(keyword, keyword, PageRequest.of(currentPage - 1, 2));
    }

    private Page<User> createMockedPage(List<User> userList) {
        return new PageImpl<>(userList);
    }

    private User mockUser() {
        return User.builder()
                .id(2)
                .name("user")
                .surname("user")
                .email("test@user.com")
                .password("user")
                .dateOfBirth(new Date(1990, 5, 15))
                .gender(UserGender.MALE)
                .city("New York")
                .country(new Country(1, "Afghanistan"))
                .personalInformation("Some personal info")
                .enabled(true)
                .role(UserRole.USER)
                .build();
    }
}
