package com.friendfinder.friendfinderrest.endpoint;

import com.friendfinder.friendfindercommon.entity.User;
import com.friendfinder.friendfindercommon.security.CurrentUser;
import com.friendfinder.friendfindercommon.service.FriendRequestService;
import com.friendfinder.friendfindercommon.service.SearchService;
import com.friendfinder.friendfindercommon.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/search/friend")
public class SearchEndpoint {

    private final SearchService searchService;
    private final UserService userService;
    private final FriendRequestService friendRequestService;


    @PostMapping("/{pageNumber}")
    public ResponseEntity<List<User>> listByPageSearch(@RequestParam String keyword,
                                           @PathVariable("pageNumber") int currentPage,
                                           @AuthenticationPrincipal CurrentUser currentUser) {
        Page<User> page = searchService.searchByKeyword(keyword,currentUser,currentPage);
        List<User> content = page.getContent();
        return ResponseEntity.ok(content);
    }
}
