package com.amazon.integration.demo.command;

import java.util.Set;
import com.amazon.infra.commandbus.AbsCommand;
import com.amazon.infra.domain.Entity;
import com.amazon.integration.demo.domain.entity.IntegTestrailProject;

public class IntegTestrailProjectGetAllCommand extends AbsCommand<Set<Entity<IntegTestrailProject>>>
{
}
