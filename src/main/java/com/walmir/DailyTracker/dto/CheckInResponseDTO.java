package com.walmir.dailytracker.dto;

import java.time.LocalDate;
import java.util.Objects;

import com.walmir.dailytracker.domain.CheckIn;

public class CheckInResponseDTO {
	private Long id;
	private LocalDate checkInDate;
	private Long challengeId;

	public CheckInResponseDTO() {
	}

	public CheckInResponseDTO(CheckIn entity) {
		this.id = entity.getId();
		this.checkInDate = entity.getCheckInDate();
		this.challengeId = entity.getChallenge().getId();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getCheckInDate() {
		return checkInDate;
	}

	public void setCheckInDate(LocalDate checkInDate) {
		this.checkInDate = checkInDate;
	}

	public Long getChallengeId() {
		return challengeId;
	}

	public void setChallengeId(Long challengeId) {
		this.challengeId = challengeId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CheckInResponseDTO other = (CheckInResponseDTO) obj;
		return Objects.equals(id, other.id);
	}
}
