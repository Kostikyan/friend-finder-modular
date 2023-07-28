package com.friendfinder.friendfinderrest.endpoint;

import com.friendfinder.friendfindercommon.entity.User;
import com.friendfinder.friendfindercommon.security.CurrentUser;
import com.friendfinder.friendfindercommon.service.FriendRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/friends")
public class FriendEndpoint {

    private final FriendRequestService friendRequestService;

    @GetMapping("/page/{pageNumber}")
    public ResponseEntity<List<User>> listByPage(@PathVariable("pageNumber") int currentPage,
                             @AuthenticationPrincipal CurrentUser currentUser) {
        Page<User> page = friendRequestService.userFriendsPageByUserId(currentUser.getUser().getId(), currentPage);
        List<User> content = page.getContent();

        return ResponseEntity.ok(content);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteFromFriends(@RequestParam("sender") User sender,
                                            @RequestParam("receiver") User receiver) {
        if (friendRequestService.delete(sender, receiver)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}