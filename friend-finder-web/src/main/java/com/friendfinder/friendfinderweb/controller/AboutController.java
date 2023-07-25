package com.friendfinder.friendfinderweb.controller;

import com.friendfinder.friendfindercommon.entity.*;
import com.friendfinder.friendfindercommon.entity.types.FriendStatus;
import com.friendfinder.friendfindercommon.security.CurrentUser;
import com.friendfinder.friendfindercommon.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users/about/profile")
@RequiredArgsConstructor
public class AboutController {
    private final WorkExperiencesService workExperiencesService;
    private final InterestsService interestsService;
    private final LanguageService languageService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final FriendRequestService friendRequestService;
    private final UserActivityService userActivityService;

    @GetMapping("/{userId}")
    public String workExperiences(@PathVariable("userId") User user, ModelMap modelMap, @AuthenticationPrincipal CurrentUser currentUser) {
        List<WorkExperiences> workExperiencesList = workExperiencesService.findAllByUserId(user.getId());
        List<Interest> interestList = interestsService.findAllByUserId(user.getId());
        List<Language> languageList = languageService.findAllByUserId(user.getId());

        modelMap.addAttribute("workExperiences", workExperiencesList);
        modelMap.addAttribute("interests", interestList);
        modelMap.addAttribute("profile", currentUser.getUser());
        modelMap.addAttribute("user", user);
        modelMap.addAttribute("languages", languageList);
        modelMap.addAttribute("friendsCount", friendRequestService.findFriendsByUserIdCount(user.getId()));
        modelMap.addAttribute("userActivity", userActivityService.getAllByUserId(user.getId()));
        return "timeline-about";
    }

    @GetMapping("/send-request")
    public String sendRequest(@RequestParam("sender") User sender,
                              @RequestParam("receiver") User receiver) {
        friendRequestService.save(FriendRequest.builder()
                .sender(sender)
                .receiver(receiver)
                .status(FriendStatus.PENDING)
                .build());
        return "redirect:/users/about/profile/" + receiver.getId();
    }

    @GetMapping("/change-password")
    public String changePasswordPage(@AuthenticationPrincipal CurrentUser currentUser, ModelMap modelMap) {
        modelMap.addAttribute("user", currentUser.getUser());
        return "edit-profile-password";
    }

    @PostMapping("/change-password")
    public String changePassword(@RequestParam("oldPass") String oldPass,
                                 @RequestParam("newPass") String newPass,
                                 @RequestParam("confPass") String confPass,
                                 @AuthenticationPrincipal CurrentUser currentUser,
                                 ModelMap modelMap) {
        User user = currentUser.getUser();
        if (passwordEncoder.matches(oldPass, user.getPassword())) {
            if (newPass.equals(confPass)) {
                String encodedPass = passwordEncoder.encode(newPass);
                user.setPassword(encodedPass);
                userService.updateUserPasswordById(encodedPass, user.getId());
                return "redirect:/posts";
            }
            modelMap.addAttribute("massage", "Password is not confirmed.");
            return "redirect:/users/about/profile/change-password";
        }
        modelMap.addAttribute("massage", "Incorrect password");
        return "redirect:/users/about/profile/change-password";
    }
}
