package com.amazon.integration.demo.command;

import java.util.Set;
import com.amazon.infra.commandbus.AbsCommand;
import com.amazon.infra.domain.Entity;
import com.amazon.integration.demo.domain.entity.MyBuildQA;

public class MyBuildQAGetAllCommand extends AbsCommand<Set<Entity<MyBuildQA>>>
{
}
