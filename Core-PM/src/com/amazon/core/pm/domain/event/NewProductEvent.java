package com.amazon.core.pm.domain.event;

public class NewProductEvent implements ProductEvent
{
    String productId;

    public NewProductEvent(String productId)
    {
        this.productId = productId;
    }
    public String getProductId()
    {
        return productId;
    }
}
