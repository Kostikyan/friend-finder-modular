package com.friendfinder.friendfinderweb.service;

import com.friendfinder.friendfinderweb.entity.Interest;
import com.friendfinder.friendfinderweb.security.CurrentUser;

import java.util.List;

public interface InterestsService {
    List<Interest> findAllByUserId(int userId);

    void interestSave(String interest, CurrentUser currentUser);
}
