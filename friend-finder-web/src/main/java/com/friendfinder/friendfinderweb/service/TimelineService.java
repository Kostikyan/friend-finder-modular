package com.friendfinder.friendfinderweb.service;

import com.friendfinder.friendfinderweb.entity.Country;
import com.friendfinder.friendfinderweb.entity.User;
import com.friendfinder.friendfinderweb.entity.UserImage;
import com.friendfinder.friendfinderweb.security.CurrentUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TimelineService {
    List<Country> findAllCountries();

    void updateUser(@ModelAttribute User user, @AuthenticationPrincipal CurrentUser currentUser);

    void updateUserProfilePic(MultipartFile profilePic, CurrentUser currentUser, UserImage userImage);

    void updateUserProfileBackgroundPic(MultipartFile bgPic, CurrentUser currentUser);
}
