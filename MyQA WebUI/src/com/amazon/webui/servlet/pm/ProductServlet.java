package com.amazon.webui.servlet.pm;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.amazon.core.pm.command.GetProductCommand;
import com.amazon.core.pm.domain.entity.Product;
import com.amazon.infra.domain.Entity;
import com.amazon.infra.system.AppSystem;
import com.amazon.webui.servlet.SystemKey;

public class ProductServlet extends HttpServlet
{
 
    /**
     * TODO
     */
    private static final long serialVersionUID = 3060177957670274177L;
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        AppSystem pmSystem = (AppSystem) req.getSession().getAttribute(SystemKey.PM.name());        
        String productId = req.getParameter("productId");
        if(productId!=null)
        {
            req.getSession().setAttribute("productId", productId);
        }
        productId = (String) req.getSession().getAttribute("productId");
        try {
            if(productId !=null)
            {
                Entity<Product> productInfo = pmSystem.getCommandBus().submit(new GetProductCommand(productId)).getResult();
                req.getSession().setAttribute("productInfo", productInfo);
            }
            resp.sendRedirect(req.getContextPath()+"/pm/Product.jsp");
        } catch (Exception e) {
            req.getSession().removeAttribute("productId");
            throw new ServletException(e);
        }        
    }
}
