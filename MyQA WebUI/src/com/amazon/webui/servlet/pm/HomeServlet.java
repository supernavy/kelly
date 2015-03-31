package com.amazon.webui.servlet.pm;

import java.io.IOException;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.amazon.core.pm.command.GetAllProductsCommand;
import com.amazon.core.pm.domain.entity.Product;
import com.amazon.infra.domain.Entity;
import com.amazon.infra.system.AppSystem;
import com.amazon.webui.servlet.SystemKey;

public class HomeServlet extends HttpServlet
{
 
    /**
     * TODO
     */
    private static final long serialVersionUID = 3060177957670274177L;
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        AppSystem pmSystem = (AppSystem) req.getSession().getAttribute(SystemKey.PM.name());        
        
        try {
            Set<Entity<Product>> allProductInfos = pmSystem.getCommandBus().submit(new GetAllProductsCommand()).getResult();
            req.getSession().setAttribute("allProductInfos", allProductInfos);
            req.getSession().setAttribute("system", pmSystem);
            resp.sendRedirect(req.getContextPath()+"/pm/Home.jsp");
        } catch (Exception e) {
            throw new ServletException(e);
        }        
    }
}
