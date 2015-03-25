package com.amazon.infra.repository.impl;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;
import com.amazon.infra.domain.Entity;
import com.amazon.infra.domain.EntitySpec;
import com.amazon.infra.repository.Repository;
import com.amazon.infra.repository.RepositoryException;

public class RepositoryMemoryImpl<T> implements Repository<T>
{
    private volatile Map<String, Entity<T>> data = new TreeMap<String, Entity<T>>();

    public RepositoryMemoryImpl()
    {
        this(null);
    }
        
    public RepositoryMemoryImpl(Map<String, Entity<T>> data)
    {
        if(data!=null)
            this.data.putAll(data);
    }

    @Override
    public synchronized Entity<T>  createEntity(T value) throws RepositoryException
    {
        String id = UUID.randomUUID().toString();
        Entity<T> entity = new EntityImpl<T>(id, value);
        data.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public synchronized Entity<T> updateEntity(String id, T body) throws RepositoryException
    {
        Entity<T> old = data.get(id);
        if (old == null) {
            throw new RepositoryException(String.format("entity [id=%s] doesn't exist", id));
        }
        ((EntityImpl<T>)old).setBody(body);
        return old;
    }

    @Override
    public synchronized Entity<T> load(String id) throws RepositoryException
    {
        return data.get(id);
    }

    @Override
    public synchronized Set<Entity<T>> findAll() throws RepositoryException
    {
        Set<Entity<T>> all = new HashSet<Entity<T>>();
        all.addAll(data.values());
        return all;
    }

    @Override
    public synchronized Set<Entity<T>> find(EntitySpec<T> filter) throws RepositoryException
    {
        Set<Entity<T>> matched = new HashSet<Entity<T>>();
        for (Entity<T> entity : findAll()) {
            if (filter.matches(entity)) {
                matched.add(entity);
            }
        }
        return matched;
    }

    @Override
    public synchronized void delete(String id) throws RepositoryException
    {
        data.remove(id);
    }

    @Override
    public synchronized void clean() throws RepositoryException
    {
        data.clear();
    }

    @Override
    public Entity<T> load(EntitySpec<T> filter) throws RepositoryException
    {
        Set<Entity<T>> results = find(filter);
        if(results.size()==0)
            return null;
        if(results.size()>1)
            throw new RepositoryException(String.format("results.size()[%s] more than 1", results.size()));
        
        return results.iterator().next();
    }
}
