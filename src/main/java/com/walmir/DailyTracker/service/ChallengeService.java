package com.walmir.dailytracker.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.walmir.dailytracker.domain.Challenge;
import com.walmir.dailytracker.domain.CheckIn;
import com.walmir.dailytracker.dto.ChallengeResponseDTO;
import com.walmir.dailytracker.repository.ChallengeRepository;
import com.walmir.dailytracker.repository.CheckInRepository;
import com.walmir.dailytracker.service.exceptions.DatabaseException;
import com.walmir.dailytracker.service.exceptions.ProgressLimitExceededException;
import com.walmir.dailytracker.service.exceptions.ResourceNotFoundException;





@Service
public class ChallengeService {

	@Autowired
	private ChallengeRepository repository;

	@Autowired
	private CheckInRepository checkInRepository;

	@Transactional(readOnly = true)
    public List<ChallengeResponseDTO> findAll() {

        List<Challenge> list = repository.findAll();

        List<ChallengeResponseDTO> dtoList = new ArrayList<>();

        for (Challenge x : list) {
            long count = checkInRepository.countByChallengeId(x.getId());
            boolean allowed = checkProgressValidity(x, count);

            ChallengeResponseDTO dto = new ChallengeResponseDTO(x, count, allowed);
            dtoList.add(dto);
        }

        return dtoList;
    }

	@Transactional(readOnly = true)
    public ChallengeResponseDTO findById(Long id) {
        Challenge entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Challenge not found"));

        long count = checkInRepository.countByChallengeId(id);
        boolean allowed = checkProgressValidity(entity, count);

        return new ChallengeResponseDTO(entity, count, allowed);
    }

	public Challenge insert(Challenge object) {
		return repository.save(object);
	}

	@Transactional
    public ChallengeResponseDTO update(Long id, ChallengeResponseDTO dto) {
        Challenge entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Challenge not found"));

        entity.setName(dto.getName());
        entity.setDurationDays(dto.getDurationDays());
        entity.setInitialDate(dto.getInitialDate());

        entity = repository.save(entity);
        long count = checkInRepository.countByChallengeId(id);
        boolean allowed = checkProgressValidity(entity, count);

        return new ChallengeResponseDTO(entity, count, allowed);
    }

	public void deleteById(Long id) {
		if (!repository.existsById(id)) {
	        throw new ResourceNotFoundException(id);
	    }

		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}

	}

	public CheckIn doCheckIn(Long challengeId) {

		Challenge entity = repository.findById(challengeId)
                .orElseThrow(() -> new ResourceNotFoundException("Challenge not found"));

        long count = checkInRepository.countByChallengeId(challengeId);

        if (!checkProgressValidity(entity, count)) {
            throw new ProgressLimitExceededException();
        }

        CheckIn checkIn = new CheckIn();
        checkIn.setChallenge(entity);
        checkIn.setCheckInDate(LocalDate.now());

        return checkInRepository.save(checkIn);
	}

	private boolean checkProgressValidity(Challenge challenge, long totalCheckIns) {
        long diasCorridos = ChronoUnit.DAYS.between(challenge.getInitialDate(), LocalDate.now()) + 1;
        return totalCheckIns < diasCorridos;
    }
}
