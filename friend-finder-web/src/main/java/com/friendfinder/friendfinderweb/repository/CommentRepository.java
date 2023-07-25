package com.friendfinder.friendfinderweb.repository;

import com.friendfinder.friendfinderweb.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    Optional<Comment> findCommentByPostId(int postId);
}
