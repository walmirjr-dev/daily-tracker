package com.walmir.dailytracker.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.walmir.dailytracker.domain.Challenge;
import com.walmir.dailytracker.domain.CheckIn;
import com.walmir.dailytracker.repository.ChallengeRepository;
import com.walmir.dailytracker.repository.CheckInRepository;



@Service
public class ChallengeService {

	@Autowired
	private ChallengeRepository repository;

	@Autowired
	private CheckInRepository checkInrepository;

	public List<Challenge> findAll() {
		return repository.findAll();
	}

	public Challenge findbyId(Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new RuntimeException("No challenge found in Id: " + id));
	}

	public Challenge insert(Challenge object) {
		return repository.save(object);
	}

	public Challenge update(Challenge newEntity, Long id) {
		Challenge entity = repository.findById(id)
				.orElseThrow(() -> new RuntimeException("No challenge found in Id: " + id));
		updateData(entity, newEntity);

		return repository.save(entity);
	}

	public void deleteById(Long id) {
		if (!repository.existsById(id)) {
	        throw new RuntimeException("No challenge found in Id: " + id);
	    }

		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new RuntimeException(e.getMessage());
		}

	}

	private void updateData(Challenge entity, Challenge newEntity) {
		entity.setName(newEntity.getName());
		entity.setInitialDate(newEntity.getInitialDate());
		entity.setDurationDays(newEntity.getDurationDays());
	}

	public CheckIn doCheckIn(Long ChallengeId) {

		Challenge challenge = repository.findById(ChallengeId).orElseThrow(() -> new RuntimeException("Challenge not found"));

		long totalCheckIns = checkInrepository.countByChallengeId(ChallengeId);

		long daysPassed = ChronoUnit.DAYS.between(challenge.getInitialDate(), LocalDate.now()) + 1;

		if (totalCheckIns >= daysPassed) {
			throw new RuntimeException("You have already done the maximum number of checkins possible to this date");
		}

		CheckIn newCheckIn = new CheckIn();

		newCheckIn.setChallenge(challenge);
		newCheckIn.setCheckInDate(LocalDate.now());

		return checkInrepository.save(newCheckIn);
	}



}
