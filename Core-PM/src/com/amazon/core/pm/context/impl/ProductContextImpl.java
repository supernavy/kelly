package com.amazon.core.pm.context.impl;

import java.util.Set;
import com.amazon.core.pm.context.ProductContext;
import com.amazon.core.pm.context.ProductContextException;
import com.amazon.core.pm.domain.entity.Product;
import com.amazon.core.pm.domain.event.ProductNewEvent;
import com.amazon.core.pm.system.PMSystem;
import com.amazon.infra.context.AbsAppContextImpl;
import com.amazon.infra.domain.Entity;
import com.amazon.infra.domain.EntitySpec;
import com.amazon.infra.repository.Repository;
import com.amazon.infra.repository.RepositoryException;
import com.amazon.infra.system.AppSystem;
import com.amazon.infra.system.AppSystemException;

public class ProductContextImpl extends AbsAppContextImpl implements ProductContext
{
    Repository<Product> productRepository;
    
    public ProductContextImpl(AppSystem appSystem) throws AppSystemException
    {
        super(appSystem);
        this.productRepository = appSystem.getRepository(PMSystem.Repository_Product);
    }
    
    @Override
    public Entity<Product> createProduct(Product product) throws ProductContextException
    {
        try {
            Entity<Product> productEntity = productRepository.createEntity(product);
            publishEvent(new ProductNewEvent(productEntity.getId()));
            return productEntity;
        } catch (Exception e) {
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

    @Override
    public void deleteProduct(String productId) throws ProductContextException
    {
        try {
            productRepository.delete(productId);
        } catch (RepositoryException e) {
            throw new ProductContextException(e);
        }
    }

    @Override
    public Set<Entity<Product>> find(EntitySpec<Product> spec) throws ProductContextException
    {
        try {
            return productRepository.find(spec);
        } catch (RepositoryException e) {
            throw new ProductContextException(e);
        }
    }

}
