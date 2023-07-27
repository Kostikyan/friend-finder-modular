package com.friendfinder.friendfinderrest.service;

import com.friendfinder.friendfindercommon.entity.Country;
import com.friendfinder.friendfindercommon.entity.Interest;
import com.friendfinder.friendfindercommon.entity.User;
import com.friendfinder.friendfindercommon.entity.types.UserGender;
import com.friendfinder.friendfindercommon.entity.types.UserRole;
import com.friendfinder.friendfindercommon.repository.InterestsRepository;
import com.friendfinder.friendfindercommon.security.CurrentUser;
import com.friendfinder.friendfindercommon.service.InterestsService;
import com.friendfinder.friendfindercommon.service.impl.InterestsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class InterestsServiceImplTest {

    private InterestsService interestsService;
    private InterestsRepository interestsRepository;

    @BeforeEach
    public void setUp() {
        interestsRepository = mock(InterestsRepository.class);
        interestsService = new InterestsServiceImpl(interestsRepository);
    }

    @Test
    void testFindAllByUserId() {
        int userId = 1;

        List<Interest> expectedInterests = new ArrayList<>();
        expectedInterests.add(new Interest());
        expectedInterests.add(new Interest());
        expectedInterests.add(new Interest());

        when(interestsRepository.findAllByUserId(userId)).thenReturn(expectedInterests);
        List<Interest> resultInterests = interestsService.findAllByUserId(userId);

        verify(interestsRepository, times(1)).findAllByUserId(userId);
        assertEquals(expectedInterests, resultInterests);
    }

    @Test
    void testInterestSave() {
        Country country = new Country(1, "Afghanistan");
        User currentUser = User.builder()
                .id(1)
                .name("user")
                .surname("user")
                .email("user1@mail.ru")
                .password("user")
                .dateOfBirth(new Date(1990, 5, 15))
                .gender(UserGender.MALE)
                .city("New York")
                .country(country)
                .personalInformation("Some personal info")
                .enabled(true)
                .role(UserRole.USER)
                .build();
        CurrentUser mockCurrentUser = new CurrentUser(currentUser);

        String interest = "Hiking";
        interestsService.interestSave(interest, mockCurrentUser);

        verify(interestsRepository, times(1)).save(any());
    }
}