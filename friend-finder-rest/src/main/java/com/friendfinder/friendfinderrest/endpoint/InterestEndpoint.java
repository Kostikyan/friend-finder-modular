package com.friendfinder.friendfinderrest.endpoint;

import com.friendfinder.friendfindercommon.security.CurrentUser;
import com.friendfinder.friendfindercommon.service.InterestsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/interests")
public class InterestEndpoint {

    private final InterestsService interestsService;

    @PostMapping("/add")
    public ResponseEntity<String> interestsAdd(@RequestParam("interest") String interest,
                                               @AuthenticationPrincipal CurrentUser currentUser) {
        interestsService.interestSave(interest, currentUser);
        return new ResponseEntity<>("interest added to user", HttpStatus.OK);
    }
}
