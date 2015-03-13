package com.amazon.core.pm.context;

import com.amazon.core.pm.domain.entity.Product;
import com.amazon.infra.domain.Entity;

public interface ProductContext
{
    public Entity<Product> createProduct(Product product) throws ProductContextException;
    public Entity<Product> loadProduct(String id) throws ProductContextException;
}
