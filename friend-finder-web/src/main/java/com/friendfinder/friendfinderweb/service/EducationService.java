package com.friendfinder.friendfinderweb.service;

import com.friendfinder.friendfinderweb.entity.Education;
import com.friendfinder.friendfinderweb.security.CurrentUser;

public interface EducationService {

    void saveEducation(Education education, CurrentUser currentUser);

}
