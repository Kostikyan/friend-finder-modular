package com.friendfinder.friendfindercommon.service.impl;


import com.friendfinder.friendfindercommon.dto.postLikeDto.PostLikeDto;
import com.friendfinder.friendfindercommon.entity.Post;
import com.friendfinder.friendfindercommon.entity.PostLike;
import com.friendfinder.friendfindercommon.entity.types.LikeStatus;
import com.friendfinder.friendfindercommon.repository.PostLikeRepository;
import com.friendfinder.friendfindercommon.repository.PostRepository;
import com.friendfinder.friendfindercommon.security.CurrentUser;
import com.friendfinder.friendfindercommon.service.LikeAndDislikeService;
import com.friendfinder.friendfindercommon.mapper.PostLikeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeAndDislikeServiceImpl implements LikeAndDislikeService {

    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;
    private final PostLikeMapper postLikeMapper;

    @Override
    @Transactional
    public void saveReaction(PostLikeDto postLikeDto, CurrentUser currentUser, Post post) {
        Optional<PostLike> byUserIdAndPostId = postLikeRepository.findByUserIdAndPostId(currentUser.getUser().getId(), post.getId());
        if (byUserIdAndPostId.isEmpty()) {
            postLikeDto.setUser(currentUser.getUser());
            postLikeDto.setPost(post);
            if (postLikeDto.getLikeStatus() == LikeStatus.LIKE) {
                post.setLikeCount(post.getLikeCount() + 1);
                postRepository.save(post);
            } else {
                post.setDislikeCount(post.getDislikeCount() + 1);
                postRepository.save(post);
            }
            PostLike postLike = postLikeMapper.map(postLikeDto);
            postLikeRepository.save(postLike);
        } else {
            PostLike postLikeDelete = byUserIdAndPostId.get();
            postLikeRepository.delete(postLikeDelete);
            if (postLikeDelete.getLikeStatus() == LikeStatus.LIKE) {
                post.setLikeCount(post.getLikeCount() - 1);
                postRepository.save(post);
            } else {
                post.setDislikeCount(post.getDislikeCount() - 1);
                postRepository.save(post);
            }
        }
    }
}


