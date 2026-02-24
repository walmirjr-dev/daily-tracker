package com.walmir.dailytracker.service.exceptions;

public class BusinessRuleViolationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public BusinessRuleViolationException(String msg) {
		super(msg);
	}
}
