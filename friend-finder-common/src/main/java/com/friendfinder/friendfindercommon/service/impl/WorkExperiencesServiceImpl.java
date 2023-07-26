package com.friendfinder.friendfindercommon.service.impl;

import com.friendfinder.friendfindercommon.entity.User;
import com.friendfinder.friendfindercommon.entity.WorkExperiences;
import com.friendfinder.friendfindercommon.repository.WorkExperiencesRepository;
import com.friendfinder.friendfindercommon.security.CurrentUser;
import com.friendfinder.friendfindercommon.service.WorkExperiencesService;
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
    public WorkExperiences saveWorkExperiences(WorkExperiences workExperiences, CurrentUser currentUser) {
        User user = currentUser.getUser();
        workExperiences.setUser(user);
        return workExperiencesRepository.save(workExperiences);
    }
}
