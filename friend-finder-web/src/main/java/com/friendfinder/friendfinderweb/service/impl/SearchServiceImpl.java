package com.friendfinder.friendfinderweb.service.impl;

import com.friendfinder.friendfinderweb.entity.User;
import com.friendfinder.friendfinderweb.repository.UserRepository;
import com.friendfinder.friendfinderweb.security.CurrentUser;
import com.friendfinder.friendfinderweb.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    private final UserRepository userRepository;

    @Override
    public Page<User> searchByKeyword(String keyword, CurrentUser currentUser, int currentPage) {
        Pageable pageable = PageRequest.of(currentPage - 1, 2);
        Page<User> byNameContainsOrSurnameContains = userRepository.findByNameContainingIgnoreCaseOrSurnameContainingIgnoreCase(keyword, keyword, pageable);
//        List<User> content = byNameContainsOrSurnameContains.getContent();
//        content.remove(currentUser.getUser());
        return byNameContainsOrSurnameContains;
    }

}