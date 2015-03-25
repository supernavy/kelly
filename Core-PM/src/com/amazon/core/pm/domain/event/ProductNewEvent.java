package com.amazon.core.pm.domain.event;

public class ProductNewEvent implements ProductEvent
{
    String productId;

    public ProductNewEvent(String productId)
    {
        this.productId = productId;
    }
    public String getProductId()
    {
        return productId;
    }
}
