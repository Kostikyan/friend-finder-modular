package com.friendfinder.friendfinderweb.service.impl;

import com.friendfinder.friendfinderweb.entity.Interest;
import com.friendfinder.friendfinderweb.repository.InterestsRepository;
import com.friendfinder.friendfinderweb.security.CurrentUser;
import com.friendfinder.friendfinderweb.service.InterestsService;
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
