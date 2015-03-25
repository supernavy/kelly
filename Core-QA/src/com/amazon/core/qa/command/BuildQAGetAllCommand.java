package com.amazon.core.qa.command;

import java.util.Set;
import com.amazon.core.qa.domain.entity.BuildQA;
import com.amazon.infra.commandbus.AbsCommand;
import com.amazon.infra.domain.Entity;

public class BuildQAGetAllCommand extends AbsCommand<Set<Entity<BuildQA>>>
{
}
