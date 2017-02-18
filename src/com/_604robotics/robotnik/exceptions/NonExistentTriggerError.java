package com._604robotics.robotnik.exceptions;

/**
 * Error thrown when the framework attempts to access a nonexistent Trigger.
 */
public class NonExistentTriggerError extends Error {
	private static final long serialVersionUID = -6789130806923912458L;

	public NonExistentTriggerError() {
		super();
	}

	public NonExistentTriggerError(String message) {
		super(message);
	}

	public NonExistentTriggerError(Throwable cause) {
		super(cause);
	}

	public NonExistentTriggerError(String message, Throwable cause) {
		super(message, cause);
	}

	public NonExistentTriggerError(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
