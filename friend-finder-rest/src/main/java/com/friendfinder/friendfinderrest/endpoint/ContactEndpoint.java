package com.friendfinder.friendfinderrest.endpoint;

import com.friendfinder.friendfindercommon.dto.contactDto.ContactFormRequestDto;
import com.friendfinder.friendfindercommon.service.impl.MailService;
import com.friendfinder.friendfinderrest.exception.custom.FilterMailContactException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contact")
@RequiredArgsConstructor
@Slf4j
public class ContactEndpoint {

    private final MailService mailService;

    @PostMapping("/send-contact-form")
    public ResponseEntity<String> sendContact(
            @RequestBody ContactFormRequestDto contactFormRequestDto
    ) {
        if (!filterMailContact(contactFormRequestDto)) {
            log.error("wrong contact form data, class: ContactEndpoint, method: sendContact", new FilterMailContactException("wrong contact form data"));
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
