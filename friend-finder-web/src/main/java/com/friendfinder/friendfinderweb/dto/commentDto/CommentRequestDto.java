package com.friendfinder.friendfinderweb.dto.commentDto;

import com.friendfinder.friendfinderweb.entity.Post;
import com.friendfinder.friendfinderweb.entity.User;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequestDto {


    private User user;
    private Post post;
    private LocalDateTime datetime;
    private String commentaryText;
}
