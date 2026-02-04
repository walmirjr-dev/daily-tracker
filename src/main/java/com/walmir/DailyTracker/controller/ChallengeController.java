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

import com.walmir.dailytracker.domain.Challenge;
import com.walmir.dailytracker.service.ChallengeService;


@RestController
@RequestMapping(value = "/challenges")
public class ChallengeController {

	@Autowired
	private ChallengeService service;

	@GetMapping
	public ResponseEntity<List<Challenge>> findAll() {

		List<Challenge> list = service.findAll();

		return ResponseEntity.ok().body(list);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Challenge> findById(@PathVariable Long id) {
		Challenge entity = service.findbyId(id);

		return ResponseEntity.ok().body(entity);
	}

	@PostMapping
	public ResponseEntity<Challenge> insert (@RequestBody Challenge entity) {
		Challenge newEntity = service.insert(entity);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newEntity.getId()).toUri();
		return ResponseEntity.created(uri).body(newEntity);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete (@PathVariable Long id) {
		service.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Challenge> update (@PathVariable Long id, @RequestBody Challenge newEntity) {
		Challenge entity = service.update(newEntity, id);
		return ResponseEntity.ok().body(entity);
	}

}
