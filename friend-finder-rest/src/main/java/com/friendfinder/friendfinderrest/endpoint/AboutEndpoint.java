package com.friendfinder.friendfinderrest.endpoint;

import com.friendfinder.friendfindercommon.dto.userDto.AboutUserDto;
import com.friendfinder.friendfindercommon.entity.User;
import com.friendfinder.friendfindercommon.security.CurrentUser;
import com.friendfinder.friendfindercommon.service.InterestsService;
import com.friendfinder.friendfindercommon.service.LanguageService;
import com.friendfinder.friendfindercommon.service.UserService;
import com.friendfinder.friendfindercommon.service.WorkExperiencesService;
import com.friendfinder.friendfinderrest.exception.ChangePasswordException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/about/profile")
@RequiredArgsConstructor
@Slf4j
public class AboutEndpoint {
    private final WorkExperiencesService workExperiencesService;
    private final InterestsService interestsService;
    private final LanguageService languageService;
    private final UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<AboutUserDto> userInfo(@PathVariable("userId") User user) {
        return ResponseEntity.ok(AboutUserDto.builder()
                .languageList(languageService.findAllByUserId(user.getId()))
                .interestList(interestsService.findAllByUserId(user.getId()))
                .workExperiencesList(workExperiencesService.findAllByUserId(user.getId()))
                .build());
    }


    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestParam("oldPass") String oldPass,
                                            @RequestParam("newPass") String newPass,
                                            @RequestParam("confPass") String confPass,
                                            @AuthenticationPrincipal CurrentUser currentUser) {
        if (userService.changePassword(oldPass, newPass, confPass, currentUser.getUser())) {
            return ResponseEntity.ok().build();
        }
        log.error("wrong data", new ChangePasswordException("wrong data"));
        return ResponseEntity.badRequest().build();
    }
}
