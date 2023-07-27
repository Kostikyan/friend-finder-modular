package com.friendfinder.friendfinderrest.endpoint;

import com.friendfinder.friendfindercommon.entity.FriendRequest;
import com.friendfinder.friendfindercommon.entity.User;
import com.friendfinder.friendfindercommon.entity.UserImage;
import com.friendfinder.friendfindercommon.entity.types.FriendStatus;
import com.friendfinder.friendfindercommon.security.CurrentUser;
import com.friendfinder.friendfindercommon.service.FriendRequestService;
import com.friendfinder.friendfindercommon.service.UserActivityService;
import com.friendfinder.friendfindercommon.service.UserImageService;
import com.friendfinder.friendfindercommon.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/image/profile")
public class UserImageProfileEndpoint {


    private final UserImageService userImageService;
    private final FriendRequestService friendRequestService;
    private final UserService userService;

    @GetMapping("/{userId}/page/{pageNumber}")
    public ResponseEntity<List<UserImage>> listByPage(@PathVariable("userId") User user, @PathVariable("pageNumber") int currentPage, ModelMap modelMap,
                                                     @ModelAttribute CurrentUser currentUser) {
        Page<UserImage> page = userImageService.userImagePageByUserId(user.getId(), currentPage);
        List<UserImage> content = page.getContent();
        return ResponseEntity.ok(content);
    }

    @GetMapping("/send-request")
    public ResponseEntity<FriendRequest> sendRequest(@RequestParam int sender,
                                                     @RequestParam int receiver) {
        Optional<User> senderId = userService.findUserById(sender);
        Optional<User> receiverId = userService.findUserById(receiver);
        FriendRequest friendRequest = new FriendRequest();
        senderId.ifPresent(friendRequest::setSender);
        receiverId.ifPresent(friendRequest::setReceiver);
        friendRequest.setStatus(FriendStatus.PENDING);
        return ResponseEntity.ok(friendRequestService.save(friendRequest));
    }

    @GetMapping("/delete/imageId")
    public ResponseEntity<UserImage> deleteImageById(@RequestParam("imageId") int id) {
        return ResponseEntity.ok(userImageService.deleteUserImageById(id));
    }
}