package com.walmir.dailytracker.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.walmir.dailytracker.domain.Challenge;
import com.walmir.dailytracker.domain.CheckIn;

public interface CheckInRepository extends JpaRepository<CheckIn, Long>  {
	long countByChallengeId(Long challengeId);

	Optional<CheckIn> findFirstByChallengeOrderByCheckInDateDesc(Challenge challenge);
}
