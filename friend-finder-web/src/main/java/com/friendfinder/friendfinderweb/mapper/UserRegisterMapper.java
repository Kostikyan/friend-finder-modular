package com.friendfinder.friendfinderweb.mapper;

import com.friendfinder.friendfinderweb.dto.userDto.UserRegisterRequestDto;
import com.friendfinder.friendfinderweb.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserRegisterMapper {
    User map(UserRegisterRequestDto requestDto);
    UserRegisterRequestDto mapToDto(User user);

}
