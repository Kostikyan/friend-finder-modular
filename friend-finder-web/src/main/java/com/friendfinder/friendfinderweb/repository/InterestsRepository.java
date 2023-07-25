package com.friendfinder.friendfinderweb.repository;

import com.friendfinder.friendfinderweb.entity.Interest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterestsRepository extends JpaRepository<Interest, Integer> {
    List<Interest> findAllByUserId(int userId);
}
