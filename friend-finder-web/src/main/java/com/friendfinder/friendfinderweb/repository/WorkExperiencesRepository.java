package com.friendfinder.friendfinderweb.repository;

import com.friendfinder.friendfinderweb.entity.WorkExperiences;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkExperiencesRepository extends JpaRepository<WorkExperiences,Integer> {
    List<WorkExperiences> findAllByUserId(int userId);
}
