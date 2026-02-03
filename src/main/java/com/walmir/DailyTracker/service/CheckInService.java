package com.walmir.dailytracker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.walmir.dailytracker.domain.CheckIn;
import com.walmir.dailytracker.repository.CheckInRepository;



@Service
public class CheckInService {

	@Autowired
	private CheckInRepository repository;

	public List<CheckIn> findAll() {
		return repository.findAll();
	}

	public CheckIn findbyId(Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new RuntimeException("No checkin found in Id: " + id));
	}

	public CheckIn insert(CheckIn object) {
		return repository.save(object);
	}

	public CheckIn update(CheckIn newEntity, Long id) {
		CheckIn entity = repository.findById(id)
				.orElseThrow(() -> new RuntimeException("No checkin found in Id: " + id));
		updateData(entity, newEntity);

		return repository.save(entity);
	}

	public void deleteById(Long id) {
		if (!repository.existsById(id)) {
	        throw new RuntimeException("No checkin found in Id: " + id);
	    }

		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new RuntimeException(e.getMessage());
		}

	}

	private void updateData(CheckIn entity, CheckIn newEntity) {
		entity.setCheckInDate(newEntity.getCheckInDate());
	}

}
