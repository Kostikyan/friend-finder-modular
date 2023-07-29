package com.friendfinder.friendfinderrest.service;

import com.friendfinder.friendfindercommon.dto.postLikeDto.PostLikeDto;
import com.friendfinder.friendfindercommon.entity.Country;
import com.friendfinder.friendfindercommon.entity.Post;
import com.friendfinder.friendfindercommon.entity.PostLike;
import com.friendfinder.friendfindercommon.entity.User;
import com.friendfinder.friendfindercommon.entity.types.LikeStatus;
import com.friendfinder.friendfindercommon.entity.types.UserGender;
import com.friendfinder.friendfindercommon.entity.types.UserRole;
import com.friendfinder.friendfindercommon.mapper.PostLikeMapper;
import com.friendfinder.friendfindercommon.repository.PostLikeRepository;
import com.friendfinder.friendfindercommon.repository.PostRepository;
import com.friendfinder.friendfindercommon.security.CurrentUser;
import com.friendfinder.friendfindercommon.service.impl.LikeAndDislikeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LikeAndDislikeServiceImplTest {

    @Mock
    private PostLikeRepository postLikeRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private PostLikeMapper postLikeMapper;

    @InjectMocks
    private LikeAndDislikeServiceImpl likeAndDislikeService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        likeAndDislikeService = new LikeAndDislikeServiceImpl(postLikeRepository,
                postRepository, postLikeMapper);
    }

    @Test
     void testSaveReactionLike() {
        CurrentUser currentUser = createUser();
        Post post = createPost();
        PostLikeDto postLikeDto = new PostLikeDto();
        postLikeDto.setLikeStatus(LikeStatus.DISLIKE);

        PostLike existingLike = new PostLike();
        existingLike.setId(1);
        existingLike.setUser(currentUser.getUser());
        existingLike.setPost(post);
        existingLike.setLikeStatus(LikeStatus.DISLIKE);


        when(postLikeRepository.findByUserIdAndPostId(currentUser.getUser().getId(), post.getId())).thenReturn(Optional.empty());
        when(postLikeMapper.map(postLikeDto)).thenReturn(new PostLike());

        PostLike result = likeAndDislikeService.saveReaction(postLikeDto, currentUser, post);

        verify(postLikeRepository, times(1)).findByUserIdAndPostId(anyInt(), anyInt());
        verify(postRepository, times(1)).save(post);
        assertNull(result);

    }

    @Test
    void testRemoveLike() {
        CurrentUser currentUser = createUser();
        Post post = createPost();
        PostLikeDto postLikeDto = new PostLikeDto();
        postLikeDto.setLikeStatus(LikeStatus.LIKE);

        PostLike existingLike = new PostLike();
        existingLike.setId(1);
        existingLike.setUser(currentUser.getUser());
        existingLike.setPost(post);
        existingLike.setLikeStatus(LikeStatus.LIKE);

        when(postLikeRepository.findByUserIdAndPostId(anyInt(), anyInt())).thenReturn(Optional.of(existingLike));

        PostLike result = likeAndDislikeService.saveReaction(postLikeDto, currentUser, post);

        verify(postLikeRepository, times(1)).findByUserIdAndPostId(anyInt(), anyInt());
        verify(postRepository, times(1)).save(post);
        verify(postLikeRepository, times(1)).delete(existingLike);
        assertNull(result);
    }

    @Test
    void testRemoveDislike() {
        CurrentUser currentUser = createUser();
        Post post = createPost();

        PostLikeDto postDislikeDto = new PostLikeDto();
        postDislikeDto.setLikeStatus(LikeStatus.DISLIKE);

        PostLike existingDislike = new PostLike();
        existingDislike.setId(1);
        existingDislike.setUser(currentUser.getUser());
        existingDislike.setPost(post);
        existingDislike.setLikeStatus(LikeStatus.DISLIKE);

        when(postLikeRepository.findByUserIdAndPostId(anyInt(), anyInt())).thenReturn(Optional.of(existingDislike));

        PostLike result = likeAndDislikeService.saveReaction(postDislikeDto, currentUser, post);

        verify(postLikeRepository, times(1)).findByUserIdAndPostId(anyInt(), anyInt());
        verify(postRepository, times(1)).save(post);
        verify(postLikeRepository, times(1)).delete(existingDislike);
        assertNull(result);
    }


    private CurrentUser createUser() {
        Country country = new Country(1, "Afghanistan");
        User currentUser = User.builder()
                .id(1)
                .name("user")
                .surname("user")
                .email("user1@mail.ru")
                .password("user")
                .dateOfBirth(new Date(1990, 5, 15))
                .gender(UserGender.MALE)
                .city("New York")
                .country(country)
                .personalInformation("Some personal info")
                .enabled(true)
                .role(UserRole.USER)
                .build();
        return new CurrentUser(currentUser);
    }

    private Post createPost() {
        CurrentUser user = createUser();
        return Post.builder()
                .description("barev")
                .imgName("image.jpg")
                .musicFileName("video.mp4")
                .postDatetime(new Date())
                .user(user.getUser())
                .build();
    }
}
