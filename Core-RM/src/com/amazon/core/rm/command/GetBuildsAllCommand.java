package com.amazon.core.rm.command;

import java.util.Set;
import com.amazon.core.rm.domain.entity.Build;
import com.amazon.infra.commandbus.AbsCommand;
import com.amazon.infra.domain.Entity;

public class GetBuildsAllCommand extends AbsCommand<Set<Entity<Build>>>
{
}
