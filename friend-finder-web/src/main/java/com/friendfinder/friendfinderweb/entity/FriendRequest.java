package com.friendfinder.friendfinderweb.entity;

import com.friendfinder.friendfinderweb.entity.types.FriendStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "friend_request")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FriendRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    private User sender;
    @ManyToOne
    private User receiver;

    @Enumerated(value = EnumType.STRING)
    private FriendStatus status;
}
