package com.amazon.integration.demo.commandhandler;

import java.util.Set;
import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.context.AppContextException;
import com.amazon.infra.domain.Entity;
import com.amazon.infra.domain.EntitySpec;
import com.amazon.integration.demo.command.MyTestrailPlanGetAllCommand;
import com.amazon.integration.demo.context.MyTestrailContext;
import com.amazon.integration.demo.domain.entity.MyTestrailPlan;

public class MyTestrailPlanGetAllCommandHandler extends AbsCommandHandler<MyTestrailPlanGetAllCommand, Set<Entity<MyTestrailPlan>>>
{
    MyTestrailContext integTestrailContext;
    
    
    public MyTestrailPlanGetAllCommandHandler(MyTestrailContext integTestrailContext)
    {
        this.integTestrailContext = integTestrailContext;
    }

    @Override
    public Set<Entity<MyTestrailPlan>> handle(MyTestrailPlanGetAllCommand command) throws CommandException
    {
        try {
            Set<Entity<MyTestrailPlan>> ret = integTestrailContext.findMyTestrailPlan(new EntitySpec<MyTestrailPlan>(){

                @Override
                public boolean matches(Entity<MyTestrailPlan> entity)
                {
                    return true;
                }});
            return ret;
        } catch (AppContextException e) {
            throw new CommandException(e);
        }
    }
    
}

