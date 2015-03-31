package com.amazon.core.qa.domain.event;

import com.amazon.core.qa.domain.entity.ProductQA;

public class ProductQAUpdateEvent extends QAEntityEvent
{
    ProductQA oldData;
    ProductQA newData;
    public ProductQAUpdateEvent(String entityId, ProductQA oldOne, ProductQA newOne)
    {
        super(entityId);
        this.oldData = oldOne;
        this.newData = newOne;
    }

    public ProductQA getOldData()
    {
        return oldData;
    }
    public ProductQA getNewData()
    {
        return newData;
    }
}
