package com.amazon.infra.repository.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import com.amazon.infra.domain.Entity;
import com.amazon.infra.domain.EntitySpec;
import com.amazon.infra.repository.Repository;
import com.amazon.infra.repository.RepositoryException;

public class RepositoryFileImpl<T> implements Repository<T>
{
    File f;
    private Map<String, Entity<T>> cache = new HashMap<String, Entity<T>>();

    public RepositoryFileImpl(File f) throws RepositoryException
    {
        this.f = f;
        load();
    }

    private void flush() throws RepositoryException
    {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            f.delete();
            f.createNewFile();
            fos = new FileOutputStream(f);
            oos = new ObjectOutputStream(fos);
            for (Entity<T> e : cache.values()) {
                oos.writeObject(e);
            }
            oos.close();
            fos.close();
        } catch (IOException e) {
            throw new RepositoryException(e);
        } finally {
            try {
                if (oos != null)
                    oos.close();
                if (fos != null)
                    fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void load() throws RepositoryException
    {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream(f);
            ois = new ObjectInputStream(fis);
            while (ois.available() > 0) {
                Entity<T> e = (Entity<T>) ois.readObject();
                cache.put(e.getId(), e);
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RepositoryException(e);
        } finally {
            try {
                if (ois != null)
                    ois.close();
                if (fis != null)
                    fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void clean() throws RepositoryException
    {
        cache.clear();
        flush();
    }

    @Override
    public Entity<T> createEntity(T value) throws RepositoryException
    {
        String id = UUID.randomUUID().toString();
        Entity<T> entity = new EntityImpl<T>(id, value);
        cache.put(entity.getId(), entity);
        flush();
        return entity;
    }

    @Override
    public Entity<T> updateEntity(String id, T body) throws RepositoryException
    {
        Entity<T> old = cache.get(id);
        if (old == null) {
            throw new RepositoryException(String.format("entity [id=%s] doesn't exist", id));
        }
        ((EntityImpl<T>) old).setBody(body);
        flush();
        return old;
    }

    @Override
    public Entity<T> load(String id) throws RepositoryException
    {
        return cache.get(id);
    }

    @Override
    public Set<Entity<T>> findAll() throws RepositoryException
    {
        Set<Entity<T>> ret = new HashSet<Entity<T>>();
        ret.addAll(cache.values());
        return ret;
    }

    @Override
    public Set<Entity<T>> find(EntitySpec<T> filter) throws RepositoryException
    {
        Set<Entity<T>> matched = new HashSet<Entity<T>>();
        for (Entity<T> e : cache.values()) {
            if (filter.matches(e)) {
                matched.add(e);
            }
        }
        return matched;
    }

    @Override
    public void delete(String id) throws RepositoryException
    {
        cache.remove(id);
        flush();
    }


}
