package com.amazon.infra.domain;

public interface Entity<T> {
	public String getId();
	public T getData();
}
