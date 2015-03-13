package com.amazon.infra.repository;

import org.junit.Test;
import com.amazon.infra.repository.RepositoryException;
import com.amazon.infra.repository.RepositoryTest.MyValue;
import com.amazon.infra.repository.impl.RepositoryMemoryImpl;

public class RepositoryImplTest {
	@Test
	public void testCreateEntity() throws RepositoryException {
		RepositoryTest.testCreateEntity(new RepositoryMemoryImpl<MyValue>(null));
	}
	
	@Test
	public void testUpdateEntity() throws RepositoryException {
		RepositoryTest.testUpdateEntity(new RepositoryMemoryImpl<MyValue>(null));
	}
	
	@Test
	public void testFindAllEntity() throws RepositoryException {
		RepositoryTest.testFindAllEntity(new RepositoryMemoryImpl<MyValue>(null));
	}
//	
//	@Test
//	public void testFindWithFilterEntity() throws RepositoryException {
//		RepositoryTest.testFindWithFilterEntity(new PersistenceMemoryImpl());
//	}
//	
//	@Test
//	public void testDeleteEntity() throws RepositoryException {
//		RepositoryTest.testDeleteEntity(new PersistenceMemoryImpl());
//	}
}
