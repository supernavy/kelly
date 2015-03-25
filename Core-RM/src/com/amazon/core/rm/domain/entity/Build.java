package com.amazon.core.rm.domain.entity;

import com.amazon.core.pm.domain.entity.Product;
import com.amazon.infra.domain.Entity;

public class Build
{
    Entity<Product> productInfo;
    String buildName;
    String patchName;

    public Build(Entity<Product> productInfo, String buildName)
    {
        this(productInfo, buildName, "Base");
    }
    
    public Build(Entity<Product> productInfo, String buildName, String patchName)
    {
        this.productInfo = productInfo;
        this.buildName = buildName;
        this.patchName = patchName;
    }
    
    public Entity<Product> getProductInfo()
    {
        return productInfo;
    }
    
    public String getBuildName()
    {
        return buildName;
    }
    
    public String getPatchName()
    {
        return patchName;
    }
    
    public String getName()
    {
        return String.format("[%s]-[%s]-[%s]", getProductInfo().getData().getName(), getBuildName(), getPatchName());
    }
    
    @Override
    public String toString()
    {
        return String.format("productInfo[%s],buildName[%s],patchName[%s]", getProductInfo(), getBuildName(), getPatchName());
    }

    @Override
    public boolean equals(Object obj)
    {
        if(!this.getClass().isInstance(obj))
            return false;
        Build b = (Build) obj;
        if(b.getProductInfo().equals(productInfo) && b.getBuildName().equals(buildName) && b.getPatchName().equals(patchName))
            return true;
        return false;
    }
}
