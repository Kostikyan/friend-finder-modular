package com.friendfinder.friendfinderweb.controller;

import com.friendfinder.friendfinderweb.entity.User;
import com.friendfinder.friendfinderweb.entity.UserImage;
import com.friendfinder.friendfinderweb.security.CurrentUser;
import com.friendfinder.friendfinderweb.service.UserActivityService;
import com.friendfinder.friendfinderweb.service.impl.TimelineServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/timeline")
@RequiredArgsConstructor
public class TimelineController {

    private final TimelineServiceImpl timelineService;
    private final UserActivityService userActivityService;

    @GetMapping()
    public String timelinePage() {
        return "timeline";
    }

    @GetMapping("/edit-basic")
    public String openEditBasicPage(@AuthenticationPrincipal CurrentUser currentUser, ModelMap modelMap) {
        User user = currentUser.getUser();
        modelMap.addAttribute("user", user);
        modelMap.addAttribute("countries", timelineService.findAllCountries());
        modelMap.addAttribute("userActivity", userActivityService.getAllByUserId(user.getId()));
        return "edit-profile-basic";
    }

    @GetMapping("/edit-work-edu")
    public String editBasicPage() {
        return "edit-profile-work-edu";
    }

    @PostMapping("/edit-basic")
    public String editBasic(@ModelAttribute User user, @AuthenticationPrincipal CurrentUser currentUser) {
        timelineService.updateUser(user, currentUser);
        return "redirect:/timeline/edit-basic";
    }

    @PostMapping("/change-profile-pic")
    public String changeProfilePic(@RequestParam("profile-pic") MultipartFile profilePic,
                                   @AuthenticationPrincipal CurrentUser currentUser, @ModelAttribute UserImage userImage) {
        timelineService.updateUserProfilePic(profilePic, currentUser, userImage);
        return "redirect:/users/profile/" + currentUser.getUser().getId();
    }

    @PostMapping("/change-profile-bg-pic")
    public String changeProfileBackgroundPic(@RequestParam("profile-bg-pic") MultipartFile bgPic, @AuthenticationPrincipal CurrentUser currentUser) {
        timelineService.updateUserProfileBackgroundPic(bgPic, currentUser);
        return "redirect:/users/profile/"+ currentUser.getUser().getId();
    }
}
