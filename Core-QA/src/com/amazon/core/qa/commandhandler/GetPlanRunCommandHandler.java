package com.amazon.core.qa.commandhandler;

import com.amazon.core.qa.command.GetPlanRunCommand;
import com.amazon.core.qa.context.PlanRunContext;
import com.amazon.core.qa.domain.entity.PlanRun;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.domain.Entity;

public class GetPlanRunCommandHandler extends AbsPlanRunCommandHandler<GetPlanRunCommand, Entity<PlanRun>>
{   
    public GetPlanRunCommandHandler(PlanRunContext context)
    { 
        super(context);
    }

    @Override
    public Entity<PlanRun> handle(GetPlanRunCommand command) throws CommandException
    {
        try {
            return getContext().load(command.getPlanRunId());
        } catch (Exception e) {
            e.printStackTrace();
            throw new CommandException(e);
        }
    }

}
