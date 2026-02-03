package com.walmir.dailytracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.walmir.dailytracker.domain.Challenge;

public interface ChallengeRepository extends JpaRepository<Challenge, Long>  {

}
