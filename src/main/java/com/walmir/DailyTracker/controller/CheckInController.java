package com.walmir.dailytracker.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.walmir.dailytracker.domain.CheckIn;
import com.walmir.dailytracker.service.ChallengeService;
import com.walmir.dailytracker.service.CheckInService;


@RestController
@RequestMapping(value = "/checkins")
public class CheckInController {

	@Autowired
	private CheckInService service;

	@Autowired
	private ChallengeService challengeService;

	@GetMapping
	public ResponseEntity<List<CheckIn>> findAll() {

		List<CheckIn> list = service.findAll();

		return ResponseEntity.ok().body(list);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<CheckIn> findById(@PathVariable Long id) {
		CheckIn entity = service.findById(id);

		return ResponseEntity.ok().body(entity);
	}

	@PostMapping("/challenge/{challengeId}")
	public ResponseEntity<CheckIn> insert(@PathVariable Long challengeId) {

	    CheckIn newEntity = challengeService.doCheckIn(challengeId);

	    URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
	            .path("/{id}")
	            .buildAndExpand(newEntity.getId())
	            .toUri();

	    return ResponseEntity.created(uri).body(newEntity);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete (@PathVariable Long id) {
		service.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<CheckIn> update (@PathVariable Long id, @RequestBody CheckIn newEntity) {
		CheckIn entity = service.update(newEntity, id);
		return ResponseEntity.ok().body(entity);
	}

}
