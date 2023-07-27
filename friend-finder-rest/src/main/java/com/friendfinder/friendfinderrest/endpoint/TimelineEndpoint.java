package com.friendfinder.friendfinderrest.endpoint;

import com.friendfinder.friendfindercommon.dto.userDto.UserUpdateRequestDto;
import com.friendfinder.friendfindercommon.dto.userDto.UserUpdateResponseDto;
import com.friendfinder.friendfindercommon.entity.User;
import com.friendfinder.friendfindercommon.entity.UserImage;
import com.friendfinder.friendfindercommon.security.CurrentUser;
import com.friendfinder.friendfindercommon.service.impl.TimelineServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/timeline")
@RequiredArgsConstructor
public class TimelineEndpoint {

    private final TimelineServiceImpl timelineService;

    @PutMapping("/edit-basic")
    public ResponseEntity<UserUpdateResponseDto> editBasic(@RequestBody UserUpdateRequestDto userUpdateRequestDto,
                                                           @AuthenticationPrincipal CurrentUser currentUser) {
        User user = timelineService.updateUser(userUpdateRequestDto, currentUser);
        return ResponseEntity.ok(
                UserUpdateResponseDto.builder()
                        .id(user.getId())
                        .name(user.getName())
                        .surname(user.getSurname())
                        .email(user.getEmail())
                        .country(user.getCountry())
                        .dateOfBirth(user.getDateOfBirth())
                        .gender(user.getGender())
                        .personalInformation(user.getPersonalInformation())
                        .city(user.getCity())
                        .build()
        );
    }

    @PutMapping("/change-profile-pic")
    public ResponseEntity<String> changeProfilePic(@RequestParam MultipartFile profilePic,
                                                  @AuthenticationPrincipal CurrentUser currentUser) {
        UserImage userImage = UserImage.builder()
                .imageName(profilePic.getName())
                .user(currentUser.getUser())
                .build();

        User user = timelineService.updateUserProfilePic(profilePic, currentUser, userImage);

        return ResponseEntity.ok("the user with id {" + user.getId() + "} has changed the profile picture");
    }

    @PutMapping("/change-profile-bg-pic")
    public ResponseEntity<String> changeProfileBackgroundPic(@RequestParam MultipartFile image,
                                                            @AuthenticationPrincipal CurrentUser currentUser) {
        User user = timelineService.updateUserProfileBackgroundPic(image, currentUser);
        return ResponseEntity.ok("the user with id {" + user.getId() + "} has changed the profile background picture");
    }
}
