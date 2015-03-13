package com.amazon.infra.domain;

public interface EntitySpec<T> {
	public boolean matches(Entity<T> entity);
}
