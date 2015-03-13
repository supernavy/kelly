package com.amazon.infra.repository;

import java.util.Set;
import org.junit.Assert;
import com.amazon.infra.domain.Entity;
import com.amazon.infra.domain.EntitySpec;
import com.amazon.infra.repository.Repository;
import com.amazon.infra.repository.RepositoryException;

public class RepositoryTest {
	public static void testCreateEntity(Repository<MyValue> repo) throws RepositoryException {
		String name = "navy";
		MyValue value = new MyValue(name);
		Entity<MyValue> entity = repo.createEntity(value);
		Assert.assertNotNull(entity.getId());
		Assert.assertEquals(name, entity.getData().getName());
	}

	public static void testUpdateEntity(Repository<MyValue> repo) throws RepositoryException {
		String name = "navy";
		String newName = "kelly";
		MyValue value = new MyValue(name);
		MyValue newValue = new MyValue(newName);
		Entity<MyValue> entity = repo.createEntity(value);
		Assert.assertEquals(name, entity.getData().getName());
		
		repo.updateEntity(entity.getId(), newValue);
		Assert.assertEquals(newName, entity.getData().getName());
	}
	
	public static void testFindAllEntity(Repository<MyValue> repo) throws RepositoryException {
	    MyValue valueA = new MyValue("A");
	    MyValue valueB = new MyValue("B");
		
		Set<Entity<MyValue>> ret = repo.findAll();
		Assert.assertEquals(0, ret.size());
		
		repo.createEntity(valueA);
		ret = repo.findAll();
		Assert.assertEquals(1, ret.size());
		
		repo.createEntity(valueB);
		ret = repo.findAll();
		Assert.assertEquals(2, ret.size());
	}	
//
//	public static void testFindWithFilterEntity(Repository p) throws RepositoryException {
//		MyEntity entityA = new MyEntity("A");
//		MyEntity entityB = new MyEntity("B");
//		entityA = p.create(entityA);
//		entityB = p.create(entityB);
//		
//		List<MyEntity> results = p.find(MyEntity.class, new MyEntityNameFilter("A"));
//		Assert.assertEquals(1, results.size());
//		MyEntity r = results.get(0);
//		Assert.assertEquals(entityA.getId(), r.getId());
//	}
//	
//	public static void testDeleteEntity(Repository p) throws RepositoryException {
//		MyEntity entityA = new MyEntity("A");
//		MyEntity entityB = new MyEntity("B");
//		entityA = p.create(entityA);
//		entityB = p.create(entityB);
//		
//		List<MyEntity> ret = p.findAll(MyEntity.class);
//		Assert.assertEquals(2, ret.size());
//		
//		p.delete(entityA);
//		ret = p.findAll(MyEntity.class);
//		Assert.assertEquals(1, ret.size());
//		
//		p.delete(entityB);
//		ret = p.findAll(MyEntity.class);
//		Assert.assertEquals(0, ret.size());
//	}
	
	public static class MyValue {
	    String name;
	    
	    public MyValue(String name)
        {
            this.name = name;
        }
        
        public String getName()
        {
            return name;
        }
		
	}
	
	public static class MyEntityFilter implements EntitySpec<MyValue> {
	    String name;
	    
	    public MyEntityFilter(String name)
        {
            this.name = name;
        }
        
        public String getName()
        {
            return name;
        }

        @Override
        public boolean matches(Entity<MyValue> entity)
        {
            return name.equals(entity.getData().getName());
        }
	}
}
