package com.friendfinder.friendfindercommon.repository;

import com.friendfinder.friendfindercommon.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    Optional<Comment> findCommentByPostId(int postId);
}
