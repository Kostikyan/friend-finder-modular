package com.friendfinder.friendfindercommon.service.impl;

import com.friendfinder.friendfindercommon.dto.commentDto.CommentRequestDto;
import com.friendfinder.friendfindercommon.entity.Comment;
import com.friendfinder.friendfindercommon.entity.Post;
import com.friendfinder.friendfindercommon.security.CurrentUser;
import com.friendfinder.friendfindercommon.service.CommentService;
import com.friendfinder.friendfindercommon.service.UserActivityService;
import com.friendfinder.friendfindercommon.mapper.CommentMapper;
import com.friendfinder.friendfindercommon.repository.CommentRepository;
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
