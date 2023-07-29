package com.friendfinder.friendfinderrest.service;

import com.friendfinder.friendfindercommon.dto.commentDto.CommentRequestDto;
import com.friendfinder.friendfindercommon.entity.*;
import com.friendfinder.friendfindercommon.entity.types.UserGender;
import com.friendfinder.friendfindercommon.entity.types.UserRole;
import com.friendfinder.friendfindercommon.mapper.CommentMapper;
import com.friendfinder.friendfindercommon.mapper.PostMapper;
import com.friendfinder.friendfindercommon.repository.CommentRepository;
import com.friendfinder.friendfindercommon.repository.PostRepository;
import com.friendfinder.friendfindercommon.repository.UserRepository;
import com.friendfinder.friendfindercommon.security.CurrentUser;
import com.friendfinder.friendfindercommon.service.FriendRequestService;
import com.friendfinder.friendfindercommon.service.UserActivityService;
import com.friendfinder.friendfindercommon.service.impl.CommentServiceImpl;
import com.friendfinder.friendfindercommon.service.impl.PostServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
 class CommentServiceTest {


    @Mock
    private CommentRepository commentRepository;

    @Mock
    private CurrentUser currentUser;

    @Mock
    private CommentMapper commentMapper;

    @Mock
    private UserActivityService userActivityService;

    @InjectMocks
    private CommentServiceImpl commentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        commentService = new CommentServiceImpl(commentRepository, userActivityService,commentMapper);
        currentUser = createUser();
    }

    @Test
    void testFindAll() {
        List<Comment> commentList = new ArrayList<>();
        Comment comment = new Comment();
        comment.setId(1);
        Comment comment2 = new Comment();
        comment2.setId(2);
        commentList.add(comment);
        commentList.add(comment2);

        when(commentRepository.findAll()).thenReturn(commentList);

        List<Comment> result = commentService.commentList();

        assertNotNull(result);
        assertEquals(commentList.size(), result.size());
        assertEquals(comment.getId(), result.get(0).getId());
        assertEquals(comment2.getId(), result.get(1).getId());
    }
    @Test
    void testAddComment(){
        CommentRequestDto commentDto = new CommentRequestDto();
        commentDto.setCommentaryText("Test comment text");
        Post post = new Post();
        CurrentUser currentUser = createUser();
        Comment savedComment = new Comment();
        when(commentMapper.map(any(CommentRequestDto.class))).thenReturn(savedComment);
        Comment result = commentService.addComment(commentDto, currentUser, post);
        assertNull(result);
    }

    @Test
    void testDeleteComment(){
        int postId = 1;
        when(commentRepository.findById(postId)).thenReturn(Optional.empty());

        Comment comment = createComment();
        when(commentRepository.findById(postId)).thenReturn(Optional.of(comment));
        Comment deleteComment = commentService.deleteComment(postId);

        assertNull(deleteComment, "Deleted comment should be null for non-existing ID.");

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

    private Comment createComment(){
        CurrentUser user = createUser();
        Post post = createPost();
        return Comment.builder()
                .id(1)
                .datetime(LocalDateTime.now())
                .user(user.getUser())
                .post(post)
                .commentaryText("This is a test comment.")
                .build();
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
