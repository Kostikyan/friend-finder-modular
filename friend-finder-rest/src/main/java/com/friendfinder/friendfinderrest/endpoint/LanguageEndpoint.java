package com.friendfinder.friendfinderrest.endpoint;

import com.friendfinder.friendfindercommon.entity.Language;
import com.friendfinder.friendfindercommon.security.CurrentUser;
import com.friendfinder.friendfindercommon.service.LanguageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/language")
@RequiredArgsConstructor
public class LanguageEndpoint {
    private final LanguageService languageService;

    @PostMapping("/add")
    public ResponseEntity<String> addLanguage(@RequestParam("language") String lang,
                                      @AuthenticationPrincipal CurrentUser current
    ) {
        languageService.save(Language.builder()
                .language(lang)
                .user(current.getUser())
                .build());
        String body = "Language created successfully \n lang: %s \n user: %s";
        return new ResponseEntity<>(String.format(body, lang, current.getUser().getName()), HttpStatus.OK);
    }
}
