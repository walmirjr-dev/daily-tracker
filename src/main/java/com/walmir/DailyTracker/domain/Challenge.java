package com.walmir.dailytracker.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_challenge")
public class Challenge implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private LocalDate initialDate;
	private Integer targetDays;
	private LocalDate endDate;
	@Column(name = "last_check_in_date")
	private LocalDate lastCheckInDate;

	@OneToMany(mappedBy = "challenge", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CheckIn> checkIns;

	public Challenge() {
	}

	public Challenge(Long id, String name, LocalDate initialDate, Integer targetDays, LocalDate endDate) {
		super();
		this.id = id;
		this.name = name;
		this.initialDate = initialDate;
		this.targetDays = targetDays;
		this.endDate = endDate;

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

	public LocalDate getLastCheckInDate() {
		return lastCheckInDate;
	}

	public void setLastCheckInDate(LocalDate lastCheckInDate) {
		this.lastCheckInDate = lastCheckInDate;
	}

	public List<CheckIn> getCheckIns() {
		return checkIns;
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
		Challenge other = (Challenge) obj;
		return Objects.equals(id, other.id);
	}

}
