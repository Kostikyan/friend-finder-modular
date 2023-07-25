package com.friendfinder.friendfinderweb.repository;

import com.friendfinder.friendfinderweb.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Integer> {

}
