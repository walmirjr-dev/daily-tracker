package com.walmir.dailytracker.service.exceptions;

public class ProgressLimitExceededException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ProgressLimitExceededException(String msg) {
		super(msg);
	}

	public ProgressLimitExceededException() {
		super("You have already done the maximum number of checkins possible to this date");
	}

}
