package com.friendfinder.friendfinderweb.service;

import com.friendfinder.friendfinderweb.entity.User;
import com.friendfinder.friendfinderweb.entity.UserActivity;

import java.util.List;

public interface UserActivityService {
    List<UserActivity> getAllByUserId(int userId);

    void save(User user, String type);
}
