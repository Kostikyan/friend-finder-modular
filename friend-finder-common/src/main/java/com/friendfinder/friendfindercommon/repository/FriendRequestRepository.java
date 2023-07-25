package com.friendfinder.friendfindercommon.repository;

import com.friendfinder.friendfindercommon.entity.FriendRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Integer> {
    List<FriendRequest> findByReceiverId(int receiverId);

    List<FriendRequest> findBySenderId(int senderId);

    Optional<FriendRequest> findBySenderIdAndReceiverId(int senderId, int receiverId);
}
