package com.friendfinder.friendfinderrest.endpoint;

import com.friendfinder.friendfindercommon.dto.commentDto.CommentRequestDto;
import com.friendfinder.friendfindercommon.dto.postDto.PostResponseDto;
import com.friendfinder.friendfindercommon.dto.userDto.UserDto;
import com.friendfinder.friendfindercommon.mapper.CommentMapper;
import com.friendfinder.friendfindercommon.mapper.PostMapper;
import com.friendfinder.friendfindercommon.mapper.UserMapper;
import com.friendfinder.friendfindercommon.service.CommentService;
import com.friendfinder.friendfindercommon.service.PostService;
import com.friendfinder.friendfindercommon.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminEndpoint {

    private final UserService userService;
    private final PostService postService;
    private final CommentService commentService;
    private final UserMapper userMapper;
    private final PostMapper postMapper;
    private final CommentMapper commentMapper;

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userMapper.mapToDtos(userService.userFindAll()));
    }

    @GetMapping("/posts")
    public ResponseEntity<List<PostResponseDto>> getAllPosts() {
        return ResponseEntity.ok(postMapper.mapResp(postService.findAll()));
    }

    @GetMapping("/comments")
    public ResponseEntity<List<CommentRequestDto>> getAllComments() {
        return ResponseEntity.ok(commentMapper.mapToDtos(commentService.commentList()));
    }

    @DeleteMapping("/users/delete/{id}")
    @Transactional
    public ResponseEntity<String> deleteUserById(@PathVariable("id") int id) {
        userService.deleteUserById(id);
        String body = "user with id {" + id + "} successfully deleted";
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @DeleteMapping("/posts/delete/{id}")
    @Transactional
    public ResponseEntity<String> deletePostById(@PathVariable("id") int id) {
        postService.deletePostId(id);
        String body = "post with id {" + id + "} successfully deleted";
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @DeleteMapping("/comments/delete/{id}")
    @Transactional
    public ResponseEntity<String> deleteCommentById(@PathVariable("id") int id) {
        commentService.deleteComment(id);
        String body = "comment with id {" + id + "} successfully deleted";
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @PutMapping("/users/block/{id}")
    @Transactional
    public ResponseEntity<String> blockUserById(@PathVariable("id") int id) {
        boolean block = userService.blockUserById(id);
        if (!block)
            return new ResponseEntity<>("wrong user id", HttpStatus.NOT_FOUND);

        String body = "user with id {" + id + "} successfully blocked";
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @PutMapping("/users/unblock/{id}")
    @Transactional
    public ResponseEntity<String> unblockUserById(@PathVariable("id") int id) {
        boolean unblock = userService.unblockUserById(id);
        if (!unblock)
            return new ResponseEntity<>("wrong user id", HttpStatus.NOT_FOUND);

        String body = "user with id {" + id + "} successfully unblocked";
        return new ResponseEntity<>(body, HttpStatus.OK);
    }
}
