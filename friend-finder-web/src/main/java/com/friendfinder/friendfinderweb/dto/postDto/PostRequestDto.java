package com.friendfinder.friendfinderweb.dto.postDto;

import com.friendfinder.friendfinderweb.entity.User;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostRequestDto {


    private String description;
    private String imgName;
    private String musicFileName;
    private Date postDatetime;
    private User user;
}
