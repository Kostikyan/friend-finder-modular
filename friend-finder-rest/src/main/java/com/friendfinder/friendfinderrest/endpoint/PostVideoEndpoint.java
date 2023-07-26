package com.friendfinder.friendfinderrest.endpoint;

import com.friendfinder.friendfindercommon.dto.commentDto.CommentRequestDto;
import com.friendfinder.friendfindercommon.dto.postLikeDto.PostLikeDto;
import com.friendfinder.friendfindercommon.entity.Comment;
import com.friendfinder.friendfindercommon.entity.Post;
import com.friendfinder.friendfindercommon.entity.PostLike;
import com.friendfinder.friendfindercommon.entity.types.LikeStatus;
import com.friendfinder.friendfindercommon.security.CurrentUser;
import com.friendfinder.friendfindercommon.service.CommentService;
import com.friendfinder.friendfindercommon.service.LikeAndDislikeService;
import com.friendfinder.friendfindercommon.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts/video")
public class PostVideoEndpoint {

    private final PostService postService;
    private final CommentService commentService;
    private final LikeAndDislikeService likeAndDislikeService;



    @GetMapping("/page/{pageNumber}")
    public ResponseEntity<List<Post>> listByPage(@PathVariable("pageNumber") int currentPage,
                                                 @AuthenticationPrincipal CurrentUser currentUser) {
        Page<Post> page = postService.postFindPageVideo(currentPage, currentUser);
        List<Post> content = page.getContent();
        return ResponseEntity.ok(content);
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
