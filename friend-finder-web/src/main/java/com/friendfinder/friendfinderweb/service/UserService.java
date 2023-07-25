package com.friendfinder.friendfinderweb.service;

import com.friendfinder.friendfinderweb.dto.userDto.UserRegisterRequestDto;
import com.friendfinder.friendfinderweb.entity.Country;
import com.friendfinder.friendfinderweb.entity.User;
import com.friendfinder.friendfinderweb.security.CurrentUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> userFindAll();

    List<Country> findAllCountries();

    List<User> userForAddFriend(CurrentUser currentUser);

    void userRegister(UserRegisterRequestDto dto);

    void updateUserPasswordById(String password, int id);

    Optional<User> findByEmail(String email);

    void save(User user);

    Optional<User> findUserById(int id);

    List<User> findAllExceptCurrentUser(int currentUserId);

    Page<User> findAll(Pageable pageable);

    void deleteUserById(int id);
    void blockUserById(int id);
    void unblockUserById(int id);
}
