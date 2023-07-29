package com.friendfinder.friendfinderrest.endpoint;

import com.friendfinder.friendfindercommon.entity.FriendRequest;
import com.friendfinder.friendfindercommon.entity.User;
import com.friendfinder.friendfindercommon.entity.types.FriendStatus;
import com.friendfinder.friendfindercommon.service.FriendRequestService;
import com.friendfinder.friendfinderrest.exception.custom.RejectFriendRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class FriendRequestEndpoint {
    private final FriendRequestService friendRequestService;

    @PostMapping("/send-request")
    public ResponseEntity<FriendRequest> sendRequest(@RequestParam("sender") User sender,
                                                     @RequestParam("receiver") User receiver) {
        return ResponseEntity.ok(friendRequestService.save(FriendRequest.builder()
                .sender(sender)
                .receiver(receiver)
                .status(FriendStatus.PENDING)
                .build()));
    }

    @PostMapping("/access-request")
    public ResponseEntity<FriendRequest> accessRequest(@RequestParam("sender") User sender,
                                                       @RequestParam("receiver") User receiver) {
        FriendRequest bySenderIdAndReceiverId = friendRequestService.findBySenderIdAndReceiverId(sender.getId(), receiver.getId());
        return ResponseEntity.ok(friendRequestService.changeStatus(bySenderIdAndReceiverId));
    }

    @PostMapping("/reject-request")
    public ResponseEntity<?> rejectRequest(@RequestParam("sender") User sender,
                                           @RequestParam("receiver") User receiver) {

        FriendRequest bySenderIdAndReceiverId = friendRequestService.findBySenderIdAndReceiverId(sender.getId(), receiver.getId());
        if(bySenderIdAndReceiverId==null){
            log.error("not found users", new RejectFriendRequestException());
            return ResponseEntity.notFound().build();
        }
        if (friendRequestService.delete(bySenderIdAndReceiverId)) {
            return ResponseEntity.noContent().build();
        }
        log.error("not found users", new RejectFriendRequestException());
        return ResponseEntity.notFound().build();
    }
}