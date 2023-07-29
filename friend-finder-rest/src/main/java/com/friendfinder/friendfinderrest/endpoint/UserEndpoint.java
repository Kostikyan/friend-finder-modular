package com.friendfinder.friendfinderrest.endpoint;

import com.friendfinder.friendfindercommon.dto.userDto.UserAuthResponseDto;
import com.friendfinder.friendfindercommon.dto.userDto.UserDto;
import com.friendfinder.friendfindercommon.dto.userDto.UserLoginRequestDto;
import com.friendfinder.friendfindercommon.dto.userDto.UserRegisterRequestDto;
import com.friendfinder.friendfindercommon.entity.User;
import com.friendfinder.friendfindercommon.mapper.UserRegisterMapper;
import com.friendfinder.friendfindercommon.service.UserService;
import com.friendfinder.friendfinderrest.exception.custom.UserLoginException;
import com.friendfinder.friendfinderrest.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * The UserEndpoint class defines RESTful endpoints related to user authentication and registration.
 * It provides methods for user login and registration.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Slf4j
public class UserEndpoint {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil tokenUtil;
    private final UserRegisterMapper userMapper;

    /**
     * Endpoint for user authentication (login).
     *
     * @param loginRequestDto The DTO containing user login credentials (email and password).
     * @return ResponseEntity containing the authentication response DTO with a JWT token.
     */
    @PostMapping("/login")
    public ResponseEntity<UserAuthResponseDto> auth(@RequestBody UserLoginRequestDto loginRequestDto) {
        Optional<User> byEmail = userService.findByEmail(loginRequestDto.getEmail());
        if (byEmail.isEmpty()) {
            log.error("User email is incorrect", new UsernameNotFoundException("username: " + loginRequestDto.getEmail()));
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        User user = byEmail.get();
        if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
            log.error("Wrong password", new UserLoginException("wrong user password"));
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String token = tokenUtil.generateToken(loginRequestDto.getEmail());
        return ResponseEntity.ok(new UserAuthResponseDto(token));
    }

    /**
     * Endpoint for user registration.
     *
     * @param registerRequestDto The DTO containing user registration information.
     * @return ResponseEntity containing the registered user's DTO.
     */
    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody UserRegisterRequestDto registerRequestDto) {
        User user = userService.userRegister(registerRequestDto);
        return ResponseEntity.ok(userMapper.mapToUserDto(user));
    }
}
