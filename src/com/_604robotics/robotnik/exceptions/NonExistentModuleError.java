package com._604robotics.robotnik.exceptions;

/**
 * Error thrown when the framework attempts to access a nonexistent Module.
 */
public class NonExistentModuleError extends Error {
	private static final long serialVersionUID = 194037194244570447L;

	public NonExistentModuleError() {
		super();
	}

	public NonExistentModuleError(String message) {
		super(message);
	}

	public NonExistentModuleError(Throwable cause) {
		super(cause);
	}

	public NonExistentModuleError(String message, Throwable cause) {
		super(message, cause);
	}

	public NonExistentModuleError(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
