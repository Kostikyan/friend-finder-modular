package com.friendfinder.friendfinderrest.endpoint;

import com.friendfinder.friendfindercommon.dto.contact.ContactFormRequestDto;
import com.friendfinder.friendfindercommon.service.impl.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contact")
@RequiredArgsConstructor
public class ContactEndpoint {

    private final MailService mailService;

    @PostMapping("/send-contact-form")
    public ResponseEntity<String> sendContact(
            @RequestBody ContactFormRequestDto contactFormRequestDto
    ) {
        if (!filterMailContact(contactFormRequestDto)) {
            return ResponseEntity.badRequest().build();
        }

        mailService.sendFromMail(
                contactFormRequestDto.getEmail(),
                contactFormRequestDto.getSubject(),
                contactFormRequestDto.getName(),
                contactFormRequestDto.getText()
        );

        return ResponseEntity.ok("Mail Sent!");
    }

    boolean filterMailContact(ContactFormRequestDto contactFormRequestDto){
        return contactFormRequestDto.getSubject() != null &&
                contactFormRequestDto.getEmail() != null &&
                contactFormRequestDto.getText() != null &&
                contactFormRequestDto.getName() != null;
    }
}
