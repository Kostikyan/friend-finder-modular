package com.friendfinder.friendfinderweb.service;

import com.friendfinder.friendfinderweb.dto.postLikeDto.PostLikeDto;
import com.friendfinder.friendfinderweb.entity.Post;
import com.friendfinder.friendfinderweb.security.CurrentUser;

public interface LikeAndDislikeService {
    void saveReaction(PostLikeDto postLikeDto, CurrentUser currentUser, Post post);
}
