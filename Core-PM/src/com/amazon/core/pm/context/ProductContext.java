package com.amazon.core.pm.context;

import java.util.Set;
import com.amazon.core.pm.domain.entity.Product;
import com.amazon.infra.domain.Entity;
import com.amazon.infra.domain.EntitySpec;

public interface ProductContext
{
    public Entity<Product> createProduct(Product product) throws ProductContextException;
    public Entity<Product> loadProduct(String productId) throws ProductContextException;
    public void deleteProduct(String productId) throws ProductContextException;
    public Set<Entity<Product>> find(EntitySpec<Product> spec) throws ProductContextException;
}
