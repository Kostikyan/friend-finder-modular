package com.friendfinder.friendfindercommon.service;

import com.friendfinder.friendfindercommon.entity.FriendRequest;
import com.friendfinder.friendfindercommon.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface FriendRequestService {


    Page<User> userFriendsPageByUserId(int userId, int pageNumber);

    FriendRequest save(FriendRequest friendRequest);

    List<User> findSenderByReceiverId(int receiverId);

    FriendRequest findBySenderIdAndReceiverId(int senderId, int receiverId);

    void delete(FriendRequest friendRequest);

    List<User> findFriendsByUserId(int userId);

    void changeStatus(FriendRequest friendRequest);

    int findFriendsByUserIdCount(int id);

    void delete(User sender, User receiver);
}
