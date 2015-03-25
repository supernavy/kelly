package com.amazon.core.qa.commandhandler;

import java.util.Set;
import com.amazon.core.qa.command.ProductQAGetAllCommand;
import com.amazon.core.qa.context.QAContext;
import com.amazon.core.qa.domain.entity.ProductQA;
import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.context.AppContextException;
import com.amazon.infra.domain.Entity;
import com.amazon.infra.domain.EntitySpec;

public class ProductQAGetAllCommandHandler extends AbsCommandHandler<ProductQAGetAllCommand, Set<Entity<ProductQA>>>
{
    QAContext qaContext;
    
    public ProductQAGetAllCommandHandler(QAContext qaContext)
    {
        this.qaContext = qaContext;
    }
    
    @Override
    public Set<Entity<ProductQA>> handle(ProductQAGetAllCommand command) throws CommandException
    {
        try {
            return qaContext.findProductQA(new EntitySpec<ProductQA>(){

                @Override
                public boolean matches(Entity<ProductQA> entity)
                {
                   return true;
                }});
        } catch (AppContextException e) {
            throw new CommandException(e);
        }
    }

}
