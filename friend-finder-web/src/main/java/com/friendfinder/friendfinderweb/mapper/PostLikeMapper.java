package com.friendfinder.friendfinderweb.mapper;

import com.friendfinder.friendfinderweb.dto.postLikeDto.PostLikeDto;
import com.friendfinder.friendfinderweb.entity.PostLike;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostLikeMapper {

    PostLike map(PostLikeDto requestDto);
}
