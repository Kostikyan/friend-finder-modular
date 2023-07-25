package com.friendfinder.friendfindercommon.service.impl;

import com.friendfinder.friendfindercommon.dto.postDto.PostRequestDto;
import com.friendfinder.friendfindercommon.entity.Country;
import com.friendfinder.friendfindercommon.entity.Post;
import com.friendfinder.friendfindercommon.entity.User;
import com.friendfinder.friendfindercommon.entity.types.UserGender;
import com.friendfinder.friendfindercommon.entity.types.UserRole;
import com.friendfinder.friendfindercommon.mapper.PostMapper;
import com.friendfinder.friendfindercommon.repository.CountryRepository;
import com.friendfinder.friendfindercommon.repository.PostRepository;
import com.friendfinder.friendfindercommon.repository.UserRepository;
import com.friendfinder.friendfindercommon.security.CurrentUser;
import com.friendfinder.friendfindercommon.service.FriendRequestService;
import com.friendfinder.friendfindercommon.service.PostService;
import com.friendfinder.friendfindercommon.service.UserActivityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class PostServiceImplTest {

    @Mock
    private PostService postService;
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private PostRepository postRepository;

    @Mock
    private CountryRepository countryRepository;

    @Mock
    private FriendRequestService friendRequestService;

    @Mock
    private UserActivityService userActivityService;

    @Mock
    private CurrentUser currentUser;

    @Mock
    private PostMapper postMapper;


    @BeforeEach
    public void beforeAll() {
        postService = new PostServiceImpl(postRepository, userRepository, friendRequestService, postMapper, userActivityService);
    }

    @Test
    void testFindAllNotNull() {
        List<Post> postList = postService.findAll();
        assertNotNull(when(postRepository.findAll()).thenReturn(postList));

    }

    @Test
    void testGetCurrentUser() {
        User user = createUser();
        System.out.println(user);
        Authentication auth = new UsernamePasswordAuthenticationToken(user, null);
        SecurityContextHolder.getContext().setAuthentication(auth);
        User current = currentUser.getUser();
        System.out.println(current);
        assertEquals(user, current);
        SecurityContextHolder.clearContext();
    }

//    @Test
//    void testPostFindPage() {
//        int pageNumber = 1;
//        int userId = 2;
//        Page<Post> page = postService.postFindPage(pageNumber, userId);
//
//        when(postRepository.findByUserIdIn(anyList(), any(Pageable.class))).thenReturn(page);
////        assertTrue("Page should be empty", page.isEmpty());
//        List<Integer> friendIds = new ArrayList<>();
//            friendIds.add(2);
//    }


    private List<Post> createPostSave() {
        List<Post> posts = new ArrayList<>();
        Post post = Post.builder()
                .description("barev")
                .postDatetime(new Date())
                .imgName("92573853309500_user-1.jpg")
                .musicFileName("6856609383300_7.mp4")
                .user(User.builder()
                        .name("Poxos")
                        .surname("Poxosyan")
                        .email("poxos@mail.com")
                        .password(passwordEncoder.encode("poxos"))
                        .country(Country.builder()
                                .name("Armenia")
                                .build())
                        .enabled(true)
                        .gender(UserGender.MALE)
                        .dateOfBirth(new Date())
                        .role(UserRole.USER)
                        .city("Gyumri")
                        .build())
                .build();
        when(postRepository.save(any(Post.class))).thenReturn(post);
        posts.add(post);
        return posts;
    }

    @Test
    void testPostSave() {
        PostRequestDto postRequestDto = PostRequestDto.builder()
                .description("barev")
                .postDatetime(new Date())
                .imgName("92573853309500_user-1.jpg")
                .musicFileName("6856609383300_7.mp4")
                .user(User.builder()
                        .name("Poxos")
                        .surname("Poxosyan")
                        .email("poxos@mail.com")
                        .password(passwordEncoder.encode("poxos"))
                        .country(Country.builder()
                                .name("Armenia")
                                .build())
                        .enabled(true)
                        .gender(UserGender.MALE)
                        .dateOfBirth(new Date())
                        .role(UserRole.USER)
                        .city("Gyumri")
                        .build())
                .build();
        assertNotNull(when(postRepository.save(postMapper.map(postRequestDto))));
    }

    private User createUser() {
        Country country = new Country();
        country.setName("Armenia");
        countryRepository.save(country);
        User user = User.builder()
                .email("poxos@mail.com")
                .password(passwordEncoder.encode("poxos"))
                .country(country)
                .enabled(true)
                .gender(UserGender.MALE)
                .dateOfBirth(new Date())
                .role(UserRole.USER)
                .city("Gyumri")
                .build();
        return userRepository.save(user);
    }
}