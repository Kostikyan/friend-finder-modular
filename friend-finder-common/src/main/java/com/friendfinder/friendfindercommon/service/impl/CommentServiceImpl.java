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
import java.util.Optional;

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
    public Comment addComment(CommentRequestDto comment, CurrentUser currentUser, Post post) {
        Comment commentSave = commentMapper.map(CommentRequestDto.builder()
                .user(currentUser.getUser())
                .post(post)
                .commentaryText(comment.getCommentaryText())
                .datetime(LocalDateTime.now())
                .build());
        userActivityService.save(currentUser.getUser(),"commented on a post");
        return commentRepository.save(commentSave);
    }

    @Override
    public Comment deleteComment(int id) {
        Optional<Comment> byId = commentRepository.findById(id);
        if (byId.isPresent()){
            Comment comment = byId.get();
            commentRepository.deleteById(comment.getId());
        }
        return null;
    }
}
