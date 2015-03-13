package com.amazon.core.qa.command;

import com.amazon.core.qa.domain.entity.Project;
import com.amazon.infra.commandbus.AbsCommand;
import com.amazon.infra.domain.Entity;

public class CreateProjectCommand extends AbsCommand<Entity<Project>>
{    
    String projectName;
    String productId;
    public CreateProjectCommand(String projectName, String productId)
    {
        this.projectName = projectName;
        this.productId = productId;
    }
    
    public String getProjectName()
    {
        return projectName;
    }

    public String getProductId()
    {
        return productId;
    }
}
