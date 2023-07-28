package com.friendfinder.friendfindercommon.service.impl;

import com.friendfinder.friendfindercommon.dto.userDto.UserRegisterRequestDto;
import com.friendfinder.friendfindercommon.entity.Country;
import com.friendfinder.friendfindercommon.entity.User;
import com.friendfinder.friendfindercommon.entity.types.UserRole;
import com.friendfinder.friendfindercommon.repository.CountryRepository;
import com.friendfinder.friendfindercommon.repository.UserRepository;
import com.friendfinder.friendfindercommon.security.CurrentUser;
import com.friendfinder.friendfindercommon.service.FriendRequestService;
import com.friendfinder.friendfindercommon.service.UserService;
import com.friendfinder.friendfindercommon.mapper.UserRegisterMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final FriendRequestService friendRequestService;
    private final UserRegisterMapper userRegisterMapper;
    private final CountryRepository countryRepository;
    private final UserRepository userRepository;

    private final MailService mailService;
    @Value("${site.url}")
    String siteUrl;

    @Override
    public List<User> userForAddFriend(CurrentUser currentUser) {
        List<User> users = userFindAll();
        List<User> userForAddFriend = new ArrayList<>();
        for (User user : users) {
            if (friendRequestService.findBySenderIdAndReceiverId(user.getId(), currentUser.getUser().getId()) == null &&
                    user.getId() != currentUser.getUser().getId() && friendRequestService.findBySenderIdAndReceiverId(currentUser.getUser().getId(),
                    user.getId()) == null) {
                userForAddFriend.add(user);
            }
        }
        return userForAddFriend;
    }

    @Override
    public List<Country> findAllCountries() {
        return countryRepository.findAll();
    }

    @Override
    public User userRegister(UserRegisterRequestDto dto) {
        User user = userRegisterMapper.map(dto);

        Optional<User> userFromDB = userRepository.findByEmail(user.getEmail());
        if (userFromDB.isEmpty()) {
            String password = user.getPassword();
            String encodedPassword = passwordEncoder.encode(password);
            user.setPassword(encodedPassword);
            user.setEnabled(false);
            user.setRole(UserRole.USER);
            UUID uuid = UUID.randomUUID();
            user.setToken(uuid.toString());
            mailService.sendMail(user.getEmail(), "Verify Email",
                    "Hi " + user.getName() + "!\nPlease verify your email by clicking on this URL:\n " +
                            siteUrl + "/verify?email=" + user.getEmail() + "&token=" + uuid
            );
            return userRepository.save(user);
        }

        return null;
    }

    @Override
    public void updateUserPasswordById(String password, int id) {
        userRepository.updateUserPasswordById(password, id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public Optional<User> findUserById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> findAllExceptCurrentUser(int currentUserId) {
        return userRepository.findAll()
                .stream()
                .filter(user -> user.getId() != currentUserId)
                .collect(Collectors.toList());
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public List<User> userFindAll() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUserById(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public boolean blockUserById(int id) {
        Optional<User> userById = userRepository.findById(id);
        if (userById.isEmpty()) return false;

        User user = userById.get();
        user.setRole(UserRole.BLOCKED);
        userRepository.save(user);
        return true;
    }

    @Override
    public boolean unblockUserById(int id) {
        Optional<User> userById = userRepository.findById(id);
        if (userById.isEmpty()) return false;

        User user = userById.get();
        user.setRole(UserRole.USER);
        userRepository.save(user);
        return true;
    }

    @Override
    public boolean changePassword(String oldPass, String newPass, String confPass, User user) {
        if (passwordEncoder.matches(oldPass, user.getPassword())) {
            if (newPass.equals(confPass)) {
                String encodedPass = passwordEncoder.encode(newPass);
                user.setPassword(encodedPass);
                userRepository.updateUserPasswordById(encodedPass, user.getId());
                return true;
            }
        }
        return false;
    }
}
