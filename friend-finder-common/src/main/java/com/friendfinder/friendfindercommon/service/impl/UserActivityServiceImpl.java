package com.friendfinder.friendfindercommon.service.impl;

import com.friendfinder.friendfindercommon.entity.User;
import com.friendfinder.friendfindercommon.entity.UserActivity;
import com.friendfinder.friendfindercommon.repository.UserActivityRepository;
import com.friendfinder.friendfindercommon.service.UserActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserActivityServiceImpl implements UserActivityService {

    private final UserActivityRepository userActivityRepository;
    @Override
    public List<UserActivity> getAllByUserId(int userId) {
        Optional<List<UserActivity>> allByUserId = userActivityRepository.findTop4ByUserIdOrderByDateTimeDesc(userId);
        return allByUserId.orElse(null);
    }

    @Override
    public void save(User user, String type) {
        userActivityRepository.save(UserActivity.builder()
                .user(user)
                .type(type)
                .dateTime(LocalDateTime.now())
                .build());
    }
}
