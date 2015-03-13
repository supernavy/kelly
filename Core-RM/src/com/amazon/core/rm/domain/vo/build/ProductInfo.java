package com.amazon.core.rm.domain.vo.build;

public class ProductInfo
{
    String productId;
    String productName;
    
    public ProductInfo(String productId, String productName)
    {
        this.productId = productId;
        this.productName = productName;
    }
    
    public String getProductId()
    {
        return productId;
    }
    
    public String getProductName()
    {
        return productName;
    }
    
    @Override
    public String toString()
    {
        // TODO Auto-generated method stub
        return super.toString();
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if(obj==null)
            return false;
        if(this.getClass().isInstance(obj))
        {
            ProductInfo pi = (ProductInfo) obj;
            if(pi.getProductId().equals(productId) && pi.getProductName().equals(productName))
            {
                return true;
            }
        }
        
        return false;
    }
}

