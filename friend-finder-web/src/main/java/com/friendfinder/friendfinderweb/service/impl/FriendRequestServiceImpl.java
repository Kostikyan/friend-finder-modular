package com.friendfinder.friendfinderweb.service.impl;

import com.friendfinder.friendfinderweb.entity.FriendRequest;
import com.friendfinder.friendfinderweb.entity.User;
import com.friendfinder.friendfinderweb.entity.types.FriendStatus;
import com.friendfinder.friendfinderweb.repository.FriendRequestRepository;
import com.friendfinder.friendfinderweb.repository.UserRepository;
import com.friendfinder.friendfinderweb.service.FriendRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FriendRequestServiceImpl implements FriendRequestService {


    private final FriendRequestRepository friendRequestRepository;
    private final UserRepository userRepository;
    private final MailService mailService;

    @Override
    public void save(FriendRequest friendRequest) {
        if (findBySenderIdAndReceiverId(friendRequest.getSender().getId(), friendRequest.getReceiver().getId()) == null
                && findBySenderIdAndReceiverId(friendRequest.getReceiver().getId(), friendRequest.getSender().getId()) == null) {
            friendRequestRepository.save(friendRequest);
        }
        mailService.sendMail(friendRequest.getReceiver().getEmail(), "You have a new friend request", "Hi, "
                + friendRequest.getReceiver().getName() + ". You have an friend request from " +
                friendRequest.getSender().getName() + " " + friendRequest.getSender().getSurname());
    }

    @Override
    public List<User> findSenderByReceiverId(int receiverId) {
        List<FriendRequest> frList = friendRequestRepository.findByReceiverId(receiverId);
        List<User> users = new ArrayList<>();
        for (FriendRequest friendRequest : frList) {
            if (friendRequest.getStatus() == FriendStatus.PENDING) {
                users.add(friendRequest.getSender());
            }
        }
        return users;
    }

    @Override
    public FriendRequest findBySenderIdAndReceiverId(int senderId, int receiverId) {
        Optional<FriendRequest> bySenderIdAndReceiverId = friendRequestRepository.findBySenderIdAndReceiverId(senderId, receiverId);
        return bySenderIdAndReceiverId.orElse(null);
    }

    @Override
    public void delete(FriendRequest friendRequest) {
        friendRequestRepository.delete(friendRequest);
    }

    @Override
    public Page<User> userFriendsPageByUserId(int userId, int pageNumber) {
        List<User> friendsByUserId = findFriendsByUserId(userId);
        List<Integer> friendsId = new ArrayList<>();
        for (User user : friendsByUserId) {
            friendsId.add(user.getId());
        }
        Sort sort = Sort.by(Sort.Order.desc("id"));
        Pageable pageable = PageRequest.of(pageNumber - 1, 12, sort);
        return userRepository.findUsersByIdIn(friendsId, pageable);
    }

    @Override
    public List<User> findFriendsByUserId(int userId) {
        List<FriendRequest> all = friendRequestRepository.findAll();
        List<User> result = new ArrayList<>();
        for (FriendRequest friendRequest : all) {
            if (friendRequest.getSender().getId() == userId && friendRequest.getStatus() == FriendStatus.ACCEPTED) {
                result.add(friendRequest.getReceiver());
            }
            if (friendRequest.getReceiver().getId() == userId && friendRequest.getStatus() == FriendStatus.ACCEPTED) {
                result.add(friendRequest.getSender());
            }
        }
        return result;
    }

    @Override
    public void changeStatus(FriendRequest friendRequest) {
        friendRequest.setStatus(FriendStatus.ACCEPTED);
        mailService.sendMail(friendRequest.getSender().getEmail(), "Your friend request is accepted",
                "Hi, " + friendRequest.getSender().getName() +
                        ". " + friendRequest.getReceiver().getName() + " accepted your request.");
        friendRequestRepository.save(friendRequest);
    }

    @Override
    public int findFriendsByUserIdCount(int id) {
        List<User> friendsByUserId = findFriendsByUserId(id);
        return friendsByUserId.size();
    }

    @Override
    public void delete(User sender, User receiver) {
        FriendRequest bySenderIdAndReceiverId = findBySenderIdAndReceiverId(sender.getId(), receiver.getId());
        FriendRequest byReceiverIdAndSenderId = findBySenderIdAndReceiverId(receiver.getId(), sender.getId());
        if (bySenderIdAndReceiverId != null) {
            delete(bySenderIdAndReceiverId);
        }
        if (byReceiverIdAndSenderId != null) {
            delete(byReceiverIdAndSenderId);
        }
    }

}
