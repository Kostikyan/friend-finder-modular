package com.friendfinder.friendfindercommon.service.impl;

import com.friendfinder.friendfindercommon.entity.Country;
import com.friendfinder.friendfindercommon.repository.CountryRepository;
import com.friendfinder.friendfindercommon.service.MainService;
import com.friendfinder.friendfindercommon.util.ImageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MainServiceImpl implements MainService {

    @Value("${post.upload.image.path}")
    private String imageUploadPath;

    @Value("${post.video.upload.image.path}")
    private String videoUploadPath;

    @Value("${user.profile.picture.path}")
    private String userProfilePicPath;

    @Value("${user.profile.background-picture.path}")
    private String userBgProfilePicPath;

    private final CountryRepository countryRepository;

    @Override
    public List<Country> findAllCountries() {
        return countryRepository.findAll();
    }

    @Override
    public @ResponseBody byte[] getImage(String imageName) {
        return ImageUtil.getBytes(imageName, imageUploadPath);
    }

    @Override
    public @ResponseBody byte[] getVideo(String imageName) {
        return ImageUtil.getBytes(imageName, videoUploadPath);
    }

    @Override
    public @ResponseBody byte[] getProfilePic(String imageName) {
        return ImageUtil.getBytes(imageName, userProfilePicPath);
    }

    @Override
    public @ResponseBody byte[] getBgProfilePic(String imageName) {
        return ImageUtil.getBytes(imageName, userBgProfilePicPath);
    }
}
