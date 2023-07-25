package com.friendfinder.friendfinderweb.service;

import com.friendfinder.friendfinderweb.entity.WorkExperiences;
import com.friendfinder.friendfinderweb.security.CurrentUser;

import java.util.List;

public interface WorkExperiencesService {
    List<WorkExperiences> findAllByUserId(int userId);

    void saveWorkExperiences(WorkExperiences workExperiences, CurrentUser currentUser);
}
