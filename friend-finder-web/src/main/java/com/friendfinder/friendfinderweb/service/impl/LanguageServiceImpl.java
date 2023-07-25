package com.friendfinder.friendfinderweb.service.impl;

import com.friendfinder.friendfinderweb.entity.Language;
import com.friendfinder.friendfinderweb.repository.LanguageRepository;
import com.friendfinder.friendfinderweb.service.LanguageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LanguageServiceImpl implements LanguageService {
    private final LanguageRepository languageRepository;

    @Override
    public List<Language> findAllByUserId(int userId) {
        return languageRepository.findAllByUserId(userId);
    }

    @Override
    public void save(Language lang) {
        languageRepository.save(lang);
    }
}
