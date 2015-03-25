package com.amazon.webui.servlet.rm;

import java.io.IOException;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.amazon.core.pm.command.GetAllProductsCommand;
import com.amazon.core.pm.domain.entity.Product;
import com.amazon.core.rm.command.GetBuildsAllCommand;
import com.amazon.core.rm.domain.entity.Build;
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
        AppSystem rmSystem = (AppSystem) req.getSession().getAttribute(SystemKey.RM.name());        
        AppSystem pmSystem = (AppSystem) req.getSession().getAttribute(SystemKey.PM.name());     
        try {
            Set<Entity<Build>> allBuildInfos = rmSystem.getCommandBus().submit(new GetBuildsAllCommand()).getResult();
            req.getSession().setAttribute("allBuildInfos", allBuildInfos);
            
            Set<Entity<Product>> allProductInfos = pmSystem.getCommandBus().submit(new GetAllProductsCommand()).getResult();
            req.getSession().setAttribute("allProductInfos", allProductInfos);
            
            resp.sendRedirect(req.getContextPath()+"/rm/Home.jsp");
        } catch (Exception e) {
            throw new ServletException(e);
        }        
    }
}
