package com.friendfinder.friendfinderweb.service.impl;

import com.friendfinder.friendfinderweb.dto.commentDto.CommentRequestDto;
import com.friendfinder.friendfinderweb.entity.Comment;
import com.friendfinder.friendfinderweb.entity.Post;
import com.friendfinder.friendfinderweb.mapper.CommentMapper;
import com.friendfinder.friendfinderweb.repository.CommentRepository;
import com.friendfinder.friendfinderweb.security.CurrentUser;
import com.friendfinder.friendfinderweb.service.CommentService;
import com.friendfinder.friendfinderweb.service.UserActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserActivityService userActivityService;
    private final CommentMapper commentMapper;

    @Override
    public List<Comment> commentList() {
        return commentRepository.findAll();
    }

    @Override
    public void addComment(CommentRequestDto comment, CurrentUser currentUser, Post post) {
        Comment commentSave = commentMapper.map(CommentRequestDto.builder()
                .user(currentUser.getUser())
                .post(post)
                .commentaryText(comment.getCommentaryText())
                .datetime(LocalDateTime.now())
                .build());
        commentRepository.save(commentSave);
        userActivityService.save(currentUser.getUser(),"commented on a post");
    }

    @Override
    public void deleteComment(int id) {
        commentRepository.deleteById(id);
    }
}
