package com.friendfinder.friendfinderweb.dto.postLikeDto;

import com.friendfinder.friendfinderweb.entity.Post;
import com.friendfinder.friendfinderweb.entity.User;
import com.friendfinder.friendfinderweb.entity.types.LikeStatus;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostLikeDto {

    private int id;
    private LikeStatus likeStatus;
    private Post post;
    private User user;
}
