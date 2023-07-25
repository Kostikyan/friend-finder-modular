package com.friendfinder.friendfindercommon.service.impl;

import com.friendfinder.friendfindercommon.entity.Country;
import com.friendfinder.friendfindercommon.entity.User;
import com.friendfinder.friendfindercommon.entity.UserImage;
import com.friendfinder.friendfindercommon.repository.CountryRepository;
import com.friendfinder.friendfindercommon.repository.UserRepository;
import com.friendfinder.friendfindercommon.security.CurrentUser;
import com.friendfinder.friendfindercommon.service.TimelineService;
import com.friendfinder.friendfindercommon.service.UserImageService;
import com.friendfinder.friendfindercommon.util.ImageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TimelineServiceImpl implements TimelineService {

    private final CountryRepository countryRepository;
    private final UserRepository userRepository;
    private final UserImageService userImageService;

    @Value("${user.profile.picture.path}")
    private String userProfilePicPath;

    @Value("${user.profile.background-picture.path}")
    private String userBgProfilePicPath;

    @Override
    public List<Country> findAllCountries() {
        return countryRepository.findAll();
    }


    @Override
    public void updateUser(User user, CurrentUser currentUser) {
        User loggedInUser = currentUser.getUser();
        loggedInUser.setName(user.getName());
        loggedInUser.setSurname(user.getSurname());
        loggedInUser.setEmail(user.getEmail());
        loggedInUser.setDateOfBirth(user.getDateOfBirth());
        loggedInUser.setGender(user.getGender());
        loggedInUser.setCity(user.getCity());
        loggedInUser.setCountry(user.getCountry());
        loggedInUser.setPersonalInformation(user.getPersonalInformation());
        userRepository.save(loggedInUser);
    }

    @Override
    public void updateUserProfilePic(MultipartFile profilePic, CurrentUser currentUser, UserImage userImage) {
        User user = currentUser.getUser();
        user.setProfilePicture(ImageUtil.uploadImage(profilePic, userProfilePicPath));
        userRepository.save(user);
        userImageService.userImageSave(userImage, currentUser);
    }

    @Override
    public void updateUserProfileBackgroundPic(MultipartFile bgPic, CurrentUser currentUser) {
        User user = currentUser.getUser();
        user.setProfileBackgroundPic(ImageUtil.uploadImage(bgPic, userBgProfilePicPath));
        userRepository.save(user);
    }
}
