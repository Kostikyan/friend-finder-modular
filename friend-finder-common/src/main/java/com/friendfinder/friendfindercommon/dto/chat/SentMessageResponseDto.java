package com.friendfinder.friendfindercommon.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SentMessageResponseDto {
    int senderId;
    int receiverId;
    String content;
}
