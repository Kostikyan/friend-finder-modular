package com.friendfinder.friendfindercommon.dto.contact;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ContactFormRequestDto {
    private String name;
    private String email;
    private String subject;
    private String text;
}
