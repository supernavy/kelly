package com.amazon.core.pm.command;

import java.util.Set;
import com.amazon.core.pm.domain.entity.Product;
import com.amazon.infra.commandbus.AbsCommand;
import com.amazon.infra.domain.Entity;

public class GetAllProductsCommand extends AbsCommand<Set<Entity<Product>>>
{
}
