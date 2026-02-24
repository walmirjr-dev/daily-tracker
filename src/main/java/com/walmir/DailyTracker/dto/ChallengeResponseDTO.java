package com.walmir.dailytracker.dto;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import com.walmir.dailytracker.domain.Challenge;

public class ChallengeResponseDTO {

	private Long id;
	private String name;
	private LocalDate initialDate;
	private Integer targetDays;
	private LocalDate lastCheckInDate;

	private LocalDate endDate;
	private long completedCheckIns;
	private Double progressPercentage;
	private long daysRemaining;

	private boolean canCheckInToday;

	public ChallengeResponseDTO() {
	}

	public ChallengeResponseDTO(Challenge entity, long totalCheckIns, boolean allowed) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.initialDate = entity.getInitialDate();
        this.endDate = entity.getEndDate();
        this.targetDays = entity.getTargetDays();
        this.lastCheckInDate = entity.getLastCheckInDate();

        this.completedCheckIns = totalCheckIns;
        this.canCheckInToday = allowed;


        long daysBetween = ChronoUnit.DAYS.between(LocalDate.now(), entity.getEndDate());
        this.daysRemaining = Math.max(0, daysBetween);

        if (targetDays > 0) {
            this.progressPercentage = (totalCheckIns * 100.0) / targetDays;
        } else {
            this.progressPercentage = 0.0;
        }
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getInitialDate() {
		return initialDate;
	}

	public void setInitialDate(LocalDate initialDate) {
		this.initialDate = initialDate;
	}

	public Integer getTargetDays() {
		return targetDays;
	}

	public void setTargetDays(Integer durationDays) {
		this.targetDays = durationDays;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public long getCompletedCheckIns() {
		return completedCheckIns;
	}

	public void setCompletedCheckIns(long completedCheckIns) {
		this.completedCheckIns = completedCheckIns;
	}

	public Double getProgressPercentage() {
		return progressPercentage;
	}

	public void setProgressPercentage(Double progressPercentage) {
		this.progressPercentage = progressPercentage;
	}

	public long getDaysRemaining() {
		return daysRemaining;
	}

	public void setDaysRemaining(long daysRemaining) {
		this.daysRemaining = daysRemaining;
	}

	public LocalDate getLastCheckInDate() {
		return lastCheckInDate;
	}

	public void setLastCheckInDate(LocalDate lastCheckInDate) {
		this.lastCheckInDate = lastCheckInDate;
	}

	public boolean isCanCheckInToday() {
		return canCheckInToday;
	}

	public void setCanCheckInToday(boolean canCheckInToday) {
		this.canCheckInToday = canCheckInToday;
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
		ChallengeResponseDTO other = (ChallengeResponseDTO) obj;
		return Objects.equals(id, other.id);
	}
}
