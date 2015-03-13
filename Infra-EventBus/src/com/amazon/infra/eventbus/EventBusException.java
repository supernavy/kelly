package com.amazon.infra.eventbus;

public class EventBusException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2092785812566112956L;

	public EventBusException(String msg) {
		super(msg);
	}
	
	public EventBusException(Exception e) {
		super(e);
	}
}
