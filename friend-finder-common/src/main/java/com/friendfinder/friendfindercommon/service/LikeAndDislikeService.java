package com.friendfinder.friendfindercommon.service;

import com.friendfinder.friendfindercommon.dto.postLikeDto.PostLikeDto;
import com.friendfinder.friendfindercommon.entity.Post;
import com.friendfinder.friendfindercommon.security.CurrentUser;

public interface LikeAndDislikeService {
    void saveReaction(PostLikeDto postLikeDto, CurrentUser currentUser, Post post);
}
