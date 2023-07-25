package com.friendfinder.friendfinderweb.service.impl;

import com.friendfinder.friendfinderweb.entity.User;
import com.friendfinder.friendfinderweb.entity.WorkExperiences;
import com.friendfinder.friendfinderweb.repository.WorkExperiencesRepository;
import com.friendfinder.friendfinderweb.security.CurrentUser;
import com.friendfinder.friendfinderweb.service.WorkExperiencesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkExperiencesServiceImpl implements WorkExperiencesService {
    private final WorkExperiencesRepository workExperiencesRepository;

    @Override
    public List<WorkExperiences> findAllByUserId(int userId) {
        return workExperiencesRepository.findAllByUserId(userId);
    }

    @Override
    public void saveWorkExperiences(WorkExperiences workExperiences, CurrentUser currentUser) {
        User user = currentUser.getUser();
        workExperiences.setUser(user);
        workExperiencesRepository.save(workExperiences);
    }
}
