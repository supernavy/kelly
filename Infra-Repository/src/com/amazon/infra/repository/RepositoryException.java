package com.amazon.infra.repository;

public class RepositoryException extends Exception {

	public RepositoryException(String msg) {
		super(msg);
	}

	public RepositoryException(Exception e) {
		super(e);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -3460511666702294955L;
	
}
