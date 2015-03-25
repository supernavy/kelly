package com.amazon.core.qa.command;

import java.util.Set;
import com.amazon.core.qa.domain.entity.ProductQA;
import com.amazon.infra.commandbus.AbsCommand;
import com.amazon.infra.domain.Entity;

public class ProductQAGetAllCommand extends AbsCommand<Set<Entity<ProductQA>>>
{
}
