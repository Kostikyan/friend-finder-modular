package com.friendfinder.friendfinderweb.service.impl;

import com.friendfinder.friendfinderweb.entity.Education;
import com.friendfinder.friendfinderweb.entity.User;
import com.friendfinder.friendfinderweb.repository.EducationRepository;
import com.friendfinder.friendfinderweb.security.CurrentUser;
import com.friendfinder.friendfinderweb.service.EducationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EducationServiceImpl implements EducationService {

    private final EducationRepository educationRepository;

    @Override
    public void saveEducation(Education education, CurrentUser currentUser) {
        User user = currentUser.getUser();
        education.setUser(user);
        educationRepository.save(education);
    }
}
