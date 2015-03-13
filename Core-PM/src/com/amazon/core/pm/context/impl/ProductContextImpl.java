package com.amazon.core.pm.context.impl;

import com.amazon.core.pm.context.ProductContext;
import com.amazon.core.pm.context.ProductContextException;
import com.amazon.core.pm.domain.entity.Product;
import com.amazon.infra.context.AbsAppContextImpl;
import com.amazon.infra.domain.Entity;
import com.amazon.infra.eventbus.EventBus;
import com.amazon.infra.repository.Repository;
import com.amazon.infra.repository.RepositoryException;

public class ProductContextImpl extends AbsAppContextImpl implements ProductContext
{
    Repository<Product> productRepository;
    
    public ProductContextImpl(EventBus eventBus, Repository<Product> productRepository)
    {
        super(eventBus);
        this.productRepository = productRepository;
    }
    
    @Override
    public Entity<Product> createProduct(Product product) throws ProductContextException
    {
        try {
            return productRepository.createEntity(product);
        } catch (RepositoryException e) {
            throw new ProductContextException(e);
        }
    }

    @Override
    public Entity<Product> loadProduct(String id) throws ProductContextException
    {
        try {
            return productRepository.load(id);
        } catch (RepositoryException e) {
            throw new ProductContextException(e);
        }
    }

}
