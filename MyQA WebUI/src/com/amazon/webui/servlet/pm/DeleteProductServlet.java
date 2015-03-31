package com.amazon.webui.servlet.pm;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.amazon.core.pm.command.DeleteProductCommand;
import com.amazon.infra.system.AppSystem;
import com.amazon.webui.servlet.SystemKey;

public class DeleteProductServlet extends HttpServlet
{

    /**
     * TODO
     */
    private static final long serialVersionUID = -3556178459176516094L;
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        String id = req.getParameter("deleteProduct.id");
        AppSystem pmSystem = (AppSystem) req.getSession().getAttribute(SystemKey.PM.name());
        try {
            pmSystem.getCommandBus().submit(new DeleteProductCommand(id)).getResult();
            System.out.println("deleted "+id);
            resp.sendRedirect(req.getContextPath()+"/pm/Home");
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
