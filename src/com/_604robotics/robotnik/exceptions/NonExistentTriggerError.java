package com._604robotics.robotnik.exceptions;

public class NonExistentTriggerError extends Error {

	private static final long serialVersionUID = -6789130806923912458L;

	public NonExistentTriggerError
() {}

	public NonExistentTriggerError
(String arg0) {}

	public NonExistentTriggerError
(Throwable arg0) {}

	public NonExistentTriggerError
(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public NonExistentTriggerError(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
