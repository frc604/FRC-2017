package com._604robotics.robotnik.exceptions;

/**
 * Error thrown when the framework attempts to access a nonexistent Action.
 */
public class NonExistentActionError extends Error {
	private static final long serialVersionUID = -5836259750395154663L;

	public NonExistentActionError() {
		super();
	}

	public NonExistentActionError(String message) {
		super(message);
	}

	public NonExistentActionError(Throwable cause) {
		super(cause);
	}

	public NonExistentActionError(String message, Throwable cause) {
		super(message, cause);
	}

	public NonExistentActionError(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
