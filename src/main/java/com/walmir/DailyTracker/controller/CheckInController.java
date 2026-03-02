package com.walmir.dailytracker.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.walmir.dailytracker.dto.CheckInResponseDTO;
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
	public ResponseEntity<List<CheckInResponseDTO>> findAll() {

		List<CheckInResponseDTO> list = service.findAll();

		return ResponseEntity.ok().body(list);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<CheckInResponseDTO> findById(@PathVariable Long id) {
		CheckInResponseDTO entity = service.findById(id);

		return ResponseEntity.ok().body(entity);
	}

	@PostMapping("/challenge/{challengeId}")
	public ResponseEntity<CheckInResponseDTO> insert(@PathVariable Long challengeId) {

	    CheckInResponseDTO newEntity = challengeService.doCheckIn(challengeId);

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

	@DeleteMapping("/challenge/{challengeId}/undo")
    public ResponseEntity<Void> undoLastCheckIn(@PathVariable Long challengeId) {
        service.undoLastCheckIn(challengeId);
        return ResponseEntity.noContent().build();
    }
}
