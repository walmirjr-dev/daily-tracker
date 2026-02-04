package com.walmir.dailytracker.controller.exceptions;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.walmir.dailytracker.service.exceptions.ProgressLimitExceededException;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ControllerExceptionHandler {

	@ExceptionHandler(ProgressLimitExceededException.class)
	public ResponseEntity<StandardError> progressLimitExceededException (ProgressLimitExceededException e, HttpServletRequest request) {
		String error = "You have already done the maximum number of checkins possible to this date";
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}

}
