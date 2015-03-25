package com.amazon.core.qa.command;

import com.amazon.core.qa.domain.entity.ProductQA;
import com.amazon.core.qa.domain.vo.productqa.Plan;
import com.amazon.infra.commandbus.AbsCommand;
import com.amazon.infra.domain.Entity;

public class ProductQAAddPlanCommand extends AbsCommand<Entity<ProductQA>>
{
    String productQAId;
    Plan plan;
    public ProductQAAddPlanCommand(String productQAId, Plan plan)
    {
        this.productQAId = productQAId;
        this.plan = plan;
    }
    public String getProductQAId()
    {
        return productQAId;
    }
    public Plan getPlan()
    {
        return plan;
    }
}
