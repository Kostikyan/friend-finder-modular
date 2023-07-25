package com.friendfinder.friendfinderweb.service;

import com.friendfinder.friendfinderweb.entity.Language;

import java.util.List;

public interface LanguageService {
    List<Language> findAllByUserId(int userId);

    void save(Language lang);
}
