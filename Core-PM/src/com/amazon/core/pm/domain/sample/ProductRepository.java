package com.amazon.core.pm.domain.sample;

import com.amazon.core.pm.domain.entity.Product;
import com.amazon.infra.repository.RepositoryException;
import com.amazon.infra.repository.impl.RepositoryMemoryImpl;

public class ProductRepository extends RepositoryMemoryImpl<Product>
{
    public ProductRepository() throws RepositoryException
    {
        Product defaultSample = new Product("Sample","Sample Product");
        this.createEntity(defaultSample);
    }
}
