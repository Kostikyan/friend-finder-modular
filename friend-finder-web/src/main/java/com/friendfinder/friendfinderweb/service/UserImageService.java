package com.friendfinder.friendfinderweb.service;

import com.friendfinder.friendfinderweb.entity.UserImage;
import com.friendfinder.friendfinderweb.security.CurrentUser;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserImageService {

    Page<UserImage> userImagePageByUserId(int userId, int pageNumber);

    List<UserImage> getUserImageById(int userId);

    void userImageSave(UserImage userImage, CurrentUser currentUser);


    void deleteUserImageById(int id);
}
