package com._604robotics.robotnik.exceptions;

/**
 * Error thrown when attempting to overwrite existing data.
 */
public class OverwriteDataError extends Error {
	private static final long serialVersionUID = 7171926907856385695L;
	
	public OverwriteDataError() {
		super();
	}

	public OverwriteDataError(String message) {
		super(message);
	}

	public OverwriteDataError(Throwable cause) {
		super(cause);
	}

	public OverwriteDataError(String message, Throwable cause) {
		super(message, cause);
	}

	public OverwriteDataError(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
