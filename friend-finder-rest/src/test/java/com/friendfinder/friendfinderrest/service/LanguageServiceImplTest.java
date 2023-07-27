package com.friendfinder.friendfinderrest.service;

import com.friendfinder.friendfindercommon.entity.Country;
import com.friendfinder.friendfindercommon.entity.User;
import com.friendfinder.friendfindercommon.entity.types.UserGender;
import com.friendfinder.friendfindercommon.entity.types.UserRole;
import com.friendfinder.friendfindercommon.security.CurrentUser;
import com.friendfinder.friendfindercommon.service.impl.LanguageServiceImpl;
import org.junit.jupiter.api.extension.ExtendWith;
import com.friendfinder.friendfindercommon.entity.Language;
import com.friendfinder.friendfindercommon.repository.LanguageRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LanguageServiceImplTest {

    @Mock
    private LanguageRepository languageRepository;

    @InjectMocks
    private LanguageServiceImpl languageService;

    @Test
    void testFindAllByUserId() {
        CurrentUser currentUser = mockCurrentUser();
        int userId = 1;
        List<Language> expectedLanguages = new ArrayList<>();
        expectedLanguages.add(new Language(1, "asd", currentUser.getUser()));
        expectedLanguages.add(new Language(2, "asdd", currentUser.getUser()));
        expectedLanguages.add(new Language(3, "asddd", currentUser.getUser()));

        when(languageRepository.findAllByUserId(userId)).thenReturn(expectedLanguages);

        List<Language> resultLanguages = languageService.findAllByUserId(userId);

        assertEquals(expectedLanguages, resultLanguages);
    }

    @Test
    void testSave() {
        User user = mockCurrentUser().getUser();
        Language language = Language.builder()
                .language("arm")
                .user(user)
                .build();

        when(languageRepository.save(language)).thenReturn(language);

        Language savedLanguage = languageService.save(language);

        assertEquals(language, savedLanguage);
        verify(languageRepository, times(1)).save(language);
    }

    private CurrentUser mockCurrentUser(){
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
        return new CurrentUser(currentUser);
    }
}
