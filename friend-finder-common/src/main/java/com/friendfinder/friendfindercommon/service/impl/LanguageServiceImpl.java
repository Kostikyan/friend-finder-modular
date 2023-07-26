package com.friendfinder.friendfindercommon.service.impl;

import com.friendfinder.friendfindercommon.entity.Language;
import com.friendfinder.friendfindercommon.repository.LanguageRepository;
import com.friendfinder.friendfindercommon.service.LanguageService;
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
    public Language save(Language lang) {
        return languageRepository.save(lang);
    }
}
