package com.friendfinder.friendfinderweb.dto.postDto;

import com.friendfinder.friendfinderweb.entity.User;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostResponseDto {


    private int id;
    private String description;
    private String imgName;
    private String musicFileName;
    private int likeCount;
    private int dislikeCount;
    private Date postDatetime;
    private User user;
}
