package com.amazon.infra.commandbus;

public class CommandBusException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8358227716526110869L;

	public CommandBusException(String msg) {
		super(msg);
	}
	
	public CommandBusException(Exception e) {
		super(e);
	}
}
