package com.friendfinder.friendfinderweb.mapper;

import com.friendfinder.friendfinderweb.dto.postDto.PostRequestDto;
import com.friendfinder.friendfinderweb.dto.postDto.PostResponseDto;
import com.friendfinder.friendfinderweb.entity.Post;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostMapper {

    Post map(PostRequestDto requestDto);

    List<PostResponseDto> mapResp(List<Post> post);
}
