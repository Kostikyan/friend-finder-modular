package com.friendfinder.friendfinderweb.mapper;

import com.friendfinder.friendfinderweb.dto.commentDto.CommentRequestDto;
import com.friendfinder.friendfinderweb.entity.Comment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    Comment map(CommentRequestDto requestDto);
}
