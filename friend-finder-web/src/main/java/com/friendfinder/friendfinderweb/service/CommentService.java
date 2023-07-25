package com.friendfinder.friendfinderweb.service;

import com.friendfinder.friendfinderweb.dto.commentDto.CommentRequestDto;
import com.friendfinder.friendfinderweb.entity.Comment;
import com.friendfinder.friendfinderweb.entity.Post;
import com.friendfinder.friendfinderweb.security.CurrentUser;

import java.util.List;

public interface CommentService {
    void addComment(CommentRequestDto comment, CurrentUser currentUser, Post post);

    void deleteComment(int id);

    List<Comment> commentList();
}
