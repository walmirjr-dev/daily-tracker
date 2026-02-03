package com.walmir.dailytracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.walmir.dailytracker.domain.CheckIn;

public interface CheckInRepository extends JpaRepository<CheckIn, Long>  {
	long countByChallengeId(Long challengeId);
}
