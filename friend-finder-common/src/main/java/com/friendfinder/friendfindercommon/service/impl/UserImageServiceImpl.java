package com.friendfinder.friendfindercommon.service.impl;

import com.friendfinder.friendfindercommon.entity.User;
import com.friendfinder.friendfindercommon.entity.UserImage;
import com.friendfinder.friendfindercommon.repository.UserImageRepository;
import com.friendfinder.friendfindercommon.repository.UserRepository;
import com.friendfinder.friendfindercommon.security.CurrentUser;
import com.friendfinder.friendfindercommon.service.UserImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserImageServiceImpl implements UserImageService {

    private final UserImageRepository userImageRepository;
    private final UserRepository userRepository;



    @Override
    public Page<UserImage> userImagePageByUserId(int userId, int pageNumber) {
        List<UserImage> userImageById = getUserImageById(userId);
        List<Integer> userImageId = new ArrayList<>();
        for (UserImage userImage : userImageById) {
            userImageId.add(userImage.getUser().getId());
        }
        Sort sort = Sort.by(Sort.Order.desc("id"));
        Pageable pageable = PageRequest.of(pageNumber - 1, 12, sort);
        return userImageRepository.findUserImagesByUserIdIn(userImageId, pageable);
    }

    @Override
    public List<UserImage> getUserImageById(int userId) {
        Optional<User> byId = userRepository.findById(userId);
        if (byId.isPresent()) {
            User user = byId.get();
            return userImageRepository.findByUserId(user.getId());
        }
        return null;
    }

    @Override
    public void userImageSave(UserImage userImage, CurrentUser currentUser) {
        userImageRepository.save(UserImage.builder()
                .user(currentUser.getUser())
                .imageName(currentUser.getUser().getProfilePicture())
                .build());
    }

    @Override
    public UserImage deleteUserImageById(int id) {
        Optional<UserImage> byId = userImageRepository.findById(id);
        if (byId.isPresent()){
            UserImage userImage = byId.get();
            userImageRepository.deleteById(userImage.getId());
        }
        return null;
    }
}
