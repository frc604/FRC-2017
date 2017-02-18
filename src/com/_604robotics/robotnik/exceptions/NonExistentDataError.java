package com._604robotics.robotnik.exceptions;

/**
 * Error thrown when the framework attempts to access a nonexistent Data.
 */
public class NonExistentDataError extends Error {
	private static final long serialVersionUID = 121078116205737454L;

	public NonExistentDataError() {
		super();
	}

	public NonExistentDataError(String message) {
		super(message);
	}

	public NonExistentDataError(Throwable cause) {
		super(cause);
	}

	public NonExistentDataError(String message, Throwable cause) {
		super(message, cause);
	}

	public NonExistentDataError(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
