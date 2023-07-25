package com.friendfinder.friendfinderrest.endpoint;


import com.friendfinder.friendfindercommon.dto.userDto.UserAuthResponseDto;
import com.friendfinder.friendfindercommon.dto.userDto.UserDto;
import com.friendfinder.friendfindercommon.dto.userDto.UserLoginRequestDto;
import com.friendfinder.friendfindercommon.dto.userDto.UserRegisterRequestDto;
import com.friendfinder.friendfindercommon.entity.User;
import com.friendfinder.friendfindercommon.mapper.UserRegisterMapper;
import com.friendfinder.friendfindercommon.service.UserService;
import com.friendfinder.friendfinderrest.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserEndpoint {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil tokenUtil;
    private final UserRegisterMapper userMapper;


    @PostMapping("/login")
    public ResponseEntity<UserAuthResponseDto> auth(@RequestBody UserLoginRequestDto loginRequestDto) {
        Optional<User> byEmail = userService.findByEmail(loginRequestDto.getEmail());
        if (byEmail.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        User user = byEmail.get();
        if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String token = tokenUtil.generateToken(loginRequestDto.getEmail());
        return ResponseEntity.ok(new UserAuthResponseDto(token));
    }


    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody UserRegisterRequestDto registerRequestDto) {
        Optional<User> byEmail = userService.findByEmail(registerRequestDto.getEmail());
        if (byEmail.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        User user = userMapper.map(registerRequestDto);
        userService.save(user);

        return ResponseEntity.ok(userMapper.mapToUserDto(user));
    }
}
