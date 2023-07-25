package com.friendfinder.friendfindercommon.entity;

import com.friendfinder.friendfindercommon.entity.types.LikeStatus;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "post_like")
public class PostLike {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    @Enumerated(value = EnumType.STRING)
    private LikeStatus likeStatus;

    @ManyToOne
    private Post post;

    @ManyToOne
    private User user;
}
