package com._604robotics.robotnik.exceptions;

public class NonExistentActionError extends Error {

	private static final long serialVersionUID = -5836259750395154663L;

	public NonExistentActionError() {}

	public NonExistentActionError(String arg0) {
		super(arg0);
	}

	public NonExistentActionError(Throwable arg0) {
		super(arg0);
	}

	public NonExistentActionError(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public NonExistentActionError(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
