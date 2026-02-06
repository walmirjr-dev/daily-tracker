package com.walmir.dailytracker.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.walmir.dailytracker.domain.Challenge;
import com.walmir.dailytracker.domain.CheckIn;
import com.walmir.dailytracker.repository.ChallengeRepository;
import com.walmir.dailytracker.repository.CheckInRepository;
import com.walmir.dailytracker.service.exceptions.DatabaseException;
import com.walmir.dailytracker.service.exceptions.ResourceNotFoundException;

import jakarta.transaction.Transactional;



@Service
public class CheckInService {

	@Autowired
	private CheckInRepository repository;

	@Autowired
	private ChallengeRepository challengeRepository;

	public List<CheckIn> findAll() {
		return repository.findAll();
	}

	public CheckIn findById(Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	public CheckIn insert(CheckIn object) {
		return repository.save(object);
	}

	public CheckIn update(CheckIn newEntity, Long id) {
		CheckIn entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(id));
		updateData(entity, newEntity);

		return repository.save(entity);
	}

	@Transactional
    public void undoLastCheckIn(Long challengeId) {
        Challenge challenge = challengeRepository.findById(challengeId)
                .orElseThrow(() -> new ResourceNotFoundException("Challenge not found"));

        Optional<CheckIn> lastCheckIn = repository.findFirstByChallengeOrderByCheckInDateDesc(challenge);

        if (lastCheckIn.isPresent()) {
            this.deleteById(lastCheckIn.get().getId());
        } else {
            throw new ResourceNotFoundException("Nenhum check-in para desfazer.");
        }
    }

	public void deleteById(Long id) {
	    CheckIn checkIn = repository.findById(id)
	            .orElseThrow(() -> new ResourceNotFoundException(id));

	    Challenge challenge = checkIn.getChallenge();

	    try {
	        repository.deleteById(id);
	        repository.flush();

	        Optional<CheckIn> lastRemaining = repository.findFirstByChallengeOrderByCheckInDateDesc(challenge);

	        if (lastRemaining.isPresent()) {
	            challenge.setLastCheckInDate(lastRemaining.get().getCheckInDate());
	        } else {
	            challenge.setLastCheckInDate(null);
	        }

	        challengeRepository.save(challenge);

	    } catch (DataIntegrityViolationException e) {
	        throw new DatabaseException(e.getMessage());
	    }
	}

	private void updateData(CheckIn entity, CheckIn newEntity) {
		entity.setCheckInDate(newEntity.getCheckInDate());
	}

}
