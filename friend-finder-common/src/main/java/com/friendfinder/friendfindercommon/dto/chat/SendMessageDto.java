package com.friendfinder.friendfindercommon.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SendMessageDto {
    int chatId;
    int receiverId;
    String content;
}
