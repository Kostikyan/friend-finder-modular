package com.friendfinder.friendfinderweb.service;

import com.friendfinder.friendfinderweb.entity.Country;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;

public interface MainService {

    List<Country> findAllCountries();

    @ResponseBody
    byte[] getImage(String imageName);

    @ResponseBody
    byte[] getVideo(String imageName);

    @ResponseBody
    byte[] getProfilePic(String imageName);

    @ResponseBody
    byte[] getBgProfilePic(String imageName);
}
