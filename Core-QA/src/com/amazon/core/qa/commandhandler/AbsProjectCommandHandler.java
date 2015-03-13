package com.amazon.core.qa.commandhandler;

import com.amazon.core.qa.context.ProjectContext;
import com.amazon.infra.commandbus.AbsCommandHandler;
import com.amazon.infra.commandbus.Command;

public abstract class AbsProjectCommandHandler<T extends Command<R>, R> extends AbsCommandHandler<T, R>
{
    ProjectContext context;
    
    public AbsProjectCommandHandler(ProjectContext context)
    {
        this.context = context;
    }

    public ProjectContext getContext()
    {
        return context;
    }
}
