package com.friendfinder.friendfinderrest.endpoint;

import com.friendfinder.friendfindercommon.dto.languageDto.LanguageCreateResponseDto;
import com.friendfinder.friendfindercommon.entity.Language;
import com.friendfinder.friendfindercommon.security.CurrentUser;
import com.friendfinder.friendfindercommon.service.LanguageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/language")
@RequiredArgsConstructor
public class LanguageEndpoint {
    private final LanguageService languageService;

    @PostMapping("/add")
    public ResponseEntity<LanguageCreateResponseDto> addLanguage(@RequestParam("language") String lang,
                                                                 @AuthenticationPrincipal CurrentUser current
    ) {
        Language save = languageService.save(Language.builder()
                .language(lang)
                .user(current.getUser())
                .build());

        return ResponseEntity.ok(LanguageCreateResponseDto.builder()
                .id(save.getId())
                .lang(save.getLanguage())
                .userId(save.getUser().getId())
                .build());
    }
}
