package com.friendfinder.friendfindercommon.service.impl;

import com.friendfinder.friendfindercommon.dto.postDto.PostRequestDto;
import com.friendfinder.friendfindercommon.dto.postDto.PostResponseDto;
import com.friendfinder.friendfindercommon.entity.Post;
import com.friendfinder.friendfindercommon.entity.User;
import com.friendfinder.friendfindercommon.repository.PostRepository;
import com.friendfinder.friendfindercommon.repository.UserRepository;
import com.friendfinder.friendfindercommon.security.CurrentUser;
import com.friendfinder.friendfindercommon.service.FriendRequestService;
import com.friendfinder.friendfindercommon.service.PostService;
import com.friendfinder.friendfindercommon.service.UserActivityService;
import com.friendfinder.friendfindercommon.util.ImageUtil;
import com.friendfinder.friendfindercommon.mapper.PostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final FriendRequestService friendRequestService;
    private final PostMapper postMapper;
    private final UserActivityService userActivityService;

    @Value("${post.upload.image.path}")
    private String postImageUploadPath;

    @Value("${post.video.upload.image.path}")
    private String postVideoUploadPath;

    @Override
    public Page<Post> postFindPage(int pageNumber, CurrentUser currentUser) {
        List<PostResponseDto> allPostFriends = getAllPostFriends(currentUser);
        List<Integer> friendIds = new ArrayList<>();
        for (PostResponseDto post : allPostFriends) {
            friendIds.add(post.getUser().getId());
        }
        Sort sort = Sort.by(Sort.Order.desc("id"));
        Pageable pageable = PageRequest.of(pageNumber - 1, 5, sort);

        return postRepository.findByUserIdIn(friendIds, pageable);
    }

    @Override
    public Page<Post> postFindPageVideo(int pageNumber, CurrentUser currentUser) {
        List<PostResponseDto> allPostFriends = getAllPostFriends(currentUser);
        List<String> videos = new ArrayList<>();
        for (PostResponseDto post : allPostFriends) {
            videos.add(post.getMusicFileName());
        }
        Sort sort = Sort.by(Sort.Order.desc("id"));
        Pageable pageable = PageRequest.of(pageNumber - 1, 10, sort);

        return postRepository.findPostsByMusicFileNameIn(videos, pageable);
    }

    @Override
    public Page<Post> postFindPageImage(int pageNumber, CurrentUser currentUser) {
        List<PostResponseDto> allPostFriends = getAllPostFriends(currentUser);
        List<String> images = new ArrayList<>();
        for (PostResponseDto post : allPostFriends) {
            images.add(post.getImgName());
        }
        Sort sort = Sort.by(Sort.Order.desc("id"));
        Pageable pageable = PageRequest.of(pageNumber - 1, 10, sort);

        return postRepository.findPostsByImgNameIn(images, pageable);
    }

    @Override
    public Page<Post> postPageByUserId(int userId, int pageNumber) {
        List<Post> posts = postUserById(userId);
        List<Integer> postId = new ArrayList<>();
        for (Post post : posts) {
            postId.add(post.getUser().getId());
        }
        Sort sort = Sort.by(Sort.Order.desc("id"));
        Pageable pageable = PageRequest.of(pageNumber - 1, 5, sort);
        return postRepository.findPostsByUserIdIn(postId, pageable);
    }

    @Override
    public Post postSave(PostRequestDto requestDto, CurrentUser currentUser, MultipartFile image, MultipartFile video) {
        String imgName = ImageUtil.uploadImage(image, postImageUploadPath);
        String musicFileName = ImageUtil.uploadImage(video, postVideoUploadPath);
        Post post = postMapper.map(PostRequestDto.builder()
                .imgName(imgName)
                .musicFileName(musicFileName)
                .postDatetime(new Date())
                .description(requestDto.getDescription())
                .user(currentUser.getUser())
                .build());
        if (imgName != null) {
            userActivityService.save(currentUser.getUser(), "posted a photo");
        } else {
            userActivityService.save(currentUser.getUser(), "posted a video");
        }
        return postRepository.save(post);
    }

    private List<PostResponseDto> getAllPostFriends(CurrentUser currentUser) {
        List<User> friendsByUserId = friendRequestService.findFriendsByUserId(currentUser.getUser().getId());
        List<Integer> friendsIds = friendsByUserId
                .stream()
                .map(User::getId)
                .toList();

        List<PostResponseDto> postList = new ArrayList<>();
        for (Integer friendsId : friendsIds) {
            postList.addAll(postMapper.mapResp(postRepository.findByUserId(friendsId)));
        }

        return postList;
    }

    @Override
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Override
    public List<Post> postUserById(int id) {
        Optional<User> byId = userRepository.findById(id);
        if (byId.isPresent()) {
            User user = byId.get();
            return postRepository.findByUserId(user.getId());
        }
        return null;

    }

    @Override
    public void deletePostId(int id) {
        postRepository.deleteById(id);
    }
}
