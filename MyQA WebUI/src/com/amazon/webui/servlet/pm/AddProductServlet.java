package com.amazon.webui.servlet.pm;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.amazon.core.pm.command.CreateProductCommand;
import com.amazon.core.pm.domain.entity.Product;
import com.amazon.infra.domain.Entity;
import com.amazon.infra.system.AppSystem;
import com.amazon.webui.servlet.SystemKey;

public class AddProductServlet extends HttpServlet
{

    /**
     * TODO
     */
    private static final long serialVersionUID = 2648157073430092340L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        String name = req.getParameter("addProduct.name");
        String desc = req.getParameter("addProduct.desc");
        AppSystem pmSystem = (AppSystem) req.getSession().getAttribute(SystemKey.PM.name());
        try {
            Entity<Product> productEntity = pmSystem.getCommandBus().submit(new CreateProductCommand(name, desc)).getResult();
            resp.sendRedirect(req.getContextPath()+"/pm/Home");
        } catch (Exception e) {
            throw new ServletException(e);
        }
        
    }
}
