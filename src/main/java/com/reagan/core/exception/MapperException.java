package com.reagan.core.exception;

public class MapperException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 763545649323691162L;

	public MapperException(String message) {
		super(message);
	}
	
	public MapperException(Throwable cause) {
		super(cause);
	}
	
	public MapperException(String message, Throwable cause) {
		super(message, cause);
	}

}
