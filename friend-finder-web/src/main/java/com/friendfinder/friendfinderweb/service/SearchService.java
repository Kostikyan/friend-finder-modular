package com.friendfinder.friendfinderweb.service;

import com.friendfinder.friendfinderweb.entity.User;
import com.friendfinder.friendfinderweb.security.CurrentUser;
import org.springframework.data.domain.Page;
import org.springframework.web.servlet.function.ServerResponse;

public interface SearchService {
    Page<User> searchByKeyword(String keyword, CurrentUser currentUser, int currentPage);

}
