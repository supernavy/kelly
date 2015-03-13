package com.amazon.core.qa.commandhandler;

import com.amazon.core.qa.context.PlanRunContext;
import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.Command;

public abstract class AbsPlanRunCommandHandler<T extends Command<R>, R> extends AbsCommandHandler<T, R>
{
    PlanRunContext context;
    
    public AbsPlanRunCommandHandler(PlanRunContext context)
    {
        this.context = context;
    }

    public PlanRunContext getContext()
    {
        return context;
    }
}
