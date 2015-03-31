package com.amazon.integration.demo.commandhandler;

import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.context.AppContextException;
import com.amazon.infra.domain.Entity;
import com.amazon.integration.demo.command.MyBuildQAUpdateTestrailPlanCommand;
import com.amazon.integration.demo.context.MyQAContext;
import com.amazon.integration.demo.domain.entity.MyBuildQA;

public class MyBuildQAUpdateTestrailPlanCommandHandler extends AbsCommandHandler<MyBuildQAUpdateTestrailPlanCommand, Entity<MyBuildQA>>
{
    MyQAContext integQAContext;
    
    
    public MyBuildQAUpdateTestrailPlanCommandHandler(MyQAContext integQAContext)
    {
        this.integQAContext = integQAContext;
    }

    @Override
    public Entity<MyBuildQA> handle(MyBuildQAUpdateTestrailPlanCommand command) throws CommandException
    {
        try {
            Entity<MyBuildQA> ret = integQAContext.updateMyBuildQAWithTestrailPlan(command.getMyBuildQAId(), command.getMyTestrailPlanId());
            return ret;
        } catch (AppContextException e) {
            throw new CommandException(e);
        }
    }
    
}
