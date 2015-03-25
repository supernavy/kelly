package com.amazon.infra.repository;

import java.util.Set;
import com.amazon.infra.domain.Entity;
import com.amazon.infra.domain.EntitySpec;

public interface Repository<T> {
    public void clean() throws RepositoryException;
	public Entity<T> createEntity(T value) throws RepositoryException;
	public Entity<T> updateEntity(String id, T value) throws RepositoryException;
	public Entity<T> load(String id) throws RepositoryException;
	public Set<Entity<T>> findAll() throws RepositoryException;
	public Set<Entity<T>> find(EntitySpec<T> filter) throws RepositoryException;
	public Entity<T> load(EntitySpec<T> filter) throws RepositoryException;
	public void delete(String id) throws RepositoryException;
}
