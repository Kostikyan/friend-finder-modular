package com.friendfinder.friendfindercommon.service.impl;

import com.friendfinder.friendfindercommon.entity.Interest;
import com.friendfinder.friendfindercommon.security.CurrentUser;
import com.friendfinder.friendfindercommon.service.InterestsService;
import com.friendfinder.friendfindercommon.repository.InterestsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InterestsServiceImpl implements InterestsService {

    private final InterestsRepository interestsRepository;

    @Override
    public List<Interest> findAllByUserId(int userId) {
        return interestsRepository.findAllByUserId(userId);
    }

    @Override
    public void interestSave(String interest, CurrentUser currentUser) {
        interestsRepository.save(Interest.builder()
                .interest(interest)
                .user(currentUser.getUser())
                .build());
    }
}
