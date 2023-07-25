package com.friendfinder.friendfindercommon.service;

import com.friendfinder.friendfindercommon.dto.commentDto.CommentRequestDto;
import com.friendfinder.friendfindercommon.entity.Comment;
import com.friendfinder.friendfindercommon.entity.Post;
import com.friendfinder.friendfindercommon.security.CurrentUser;

import java.util.List;

public interface CommentService {
    void addComment(CommentRequestDto comment, CurrentUser currentUser, Post post);

    void deleteComment(int id);

    List<Comment> commentList();
}
