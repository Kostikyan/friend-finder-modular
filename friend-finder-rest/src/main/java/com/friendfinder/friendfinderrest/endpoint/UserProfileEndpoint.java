package com.friendfinder.friendfinderrest.endpoint;

import com.friendfinder.friendfindercommon.dto.commentDto.CommentRequestDto;
import com.friendfinder.friendfindercommon.dto.postDto.PostRequestDto;
import com.friendfinder.friendfindercommon.dto.postLikeDto.PostLikeDto;
import com.friendfinder.friendfindercommon.entity.*;
import com.friendfinder.friendfindercommon.entity.types.FriendStatus;
import com.friendfinder.friendfindercommon.entity.types.LikeStatus;
import com.friendfinder.friendfindercommon.security.CurrentUser;
import com.friendfinder.friendfindercommon.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/profile")
public class UserProfileEndpoint {

    private final PostService postService;
    private final CommentService commentService;
    private final LikeAndDislikeService likeAndDislikeService;
    private final FriendRequestService friendRequestService;
    private final UserService userService;

    @GetMapping("/{userId}/page/{pageNumber}")
    public ResponseEntity<List<Post>> listByPage(@PathVariable("userId") User user,
                                                 @PathVariable("pageNumber") int currentPage) {
        Page<Post> page = postService.postPageByUserId(user.getId(), currentPage);
        List<Post> content = page.getContent();
        ResponseEntity.ok(content);
        return ResponseEntity.ok(content);
    }

    @PostMapping("/add")
    public ResponseEntity<Post> postAdd(PostRequestDto requestDto,
                                        @AuthenticationPrincipal CurrentUser currentUser,
                                        @RequestParam MultipartFile image,
                                        @RequestParam MultipartFile video) {
        return ResponseEntity.ok(postService.postSave(requestDto, currentUser, image, video));
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

    @DeleteMapping("/delete")
    public ResponseEntity<Post> deletePostById(@RequestParam("id") int id) {
        return ResponseEntity.ok(postService.deletePostId(id));

    }

    @PostMapping("/reaction/like/{postId}")
    public ResponseEntity<PostLike> addLike(PostLikeDto postLikeDto,
                                            @AuthenticationPrincipal CurrentUser currentUser,
                                            @PathVariable(name = "postId") Post post) {
        postLikeDto.setLikeStatus(LikeStatus.LIKE);
        return ResponseEntity.ok(likeAndDislikeService.saveReaction(postLikeDto, currentUser, post));
    }

    @PostMapping("/reaction/dislike/{postId}")
    public ResponseEntity<PostLike> addDislike(PostLikeDto postLikeDto,
                                               @AuthenticationPrincipal CurrentUser currentUser,
                                               @PathVariable(name = "postId") Post post) {
        postLikeDto.setLikeStatus(LikeStatus.DISLIKE);
        return ResponseEntity.ok(likeAndDislikeService.saveReaction(postLikeDto, currentUser, post));
    }


    @PostMapping("/comment/{postId}")
    public ResponseEntity<Comment> addComment(CommentRequestDto comment,
                                              @AuthenticationPrincipal CurrentUser currentUser,
                                              @PathVariable("postId") Post post) {
        return ResponseEntity.ok(commentService.addComment(comment, currentUser, post));
    }

    @DeleteMapping("/comment/delete")
    public ResponseEntity<Comment> removeComment(@RequestParam("id") int id) {
        return ResponseEntity.ok(commentService.deleteComment(id));
    }
}
