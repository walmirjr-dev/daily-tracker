package com.walmir.dailytracker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.walmir.dailytracker.domain.CheckIn;
import com.walmir.dailytracker.repository.CheckInRepository;
import com.walmir.dailytracker.service.exceptions.DatabaseException;
import com.walmir.dailytracker.service.exceptions.ResourceNotFoundException;



@Service
public class CheckInService {

	@Autowired
	private CheckInRepository repository;

	public List<CheckIn> findAll() {
		return repository.findAll();
	}

	public CheckIn findbyId(Long id) {
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

	private void updateData(CheckIn entity, CheckIn newEntity) {
		entity.setCheckInDate(newEntity.getCheckInDate());
	}

}
