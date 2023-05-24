package com.multicampus.lottomachine.exception;

public class LoginAttemptsExceededException extends Exception {
	public LoginAttemptsExceededException(String message) {
		super(message);
	}
}
