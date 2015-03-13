package com.amazon.core.qa.commandhandler;

import com.amazon.core.qa.command.CreatePlanRunCommand;
import com.amazon.core.qa.context.PlanRunContext;
import com.amazon.core.qa.domain.entity.PlanRun;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.domain.Entity;

public class CreatePlanRunCommandHandler extends AbsPlanRunCommandHandler<CreatePlanRunCommand, Entity<PlanRun>>
{   
    public CreatePlanRunCommandHandler(PlanRunContext context)
    {
        super(context);
    }

    @Override
    public Entity<PlanRun> handle(CreatePlanRunCommand command) throws CommandException
    {
        try {
            return getContext().createTestPlanRun(command.getProjectId(), command.getPlanName(), command.getBuildId());
        } catch (Exception e) {
            e.printStackTrace();
            throw new CommandException(e);
        }
    }

}
