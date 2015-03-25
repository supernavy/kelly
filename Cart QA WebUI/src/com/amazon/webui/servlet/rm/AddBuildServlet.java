package com.amazon.webui.servlet.rm;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.amazon.core.rm.command.CreateBuildCommand;
import com.amazon.core.rm.domain.entity.Build;
import com.amazon.infra.domain.Entity;
import com.amazon.infra.system.AppSystem;
import com.amazon.webui.servlet.SystemKey;

public class AddBuildServlet extends HttpServlet
{

    /**
     * TODO
     */
    private static final long serialVersionUID = 2648157073430092340L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        String productId = req.getParameter("productId");
        String buildName = req.getParameter("baseName");
        String patchName = req.getParameter("patchName");
        AppSystem rmSystem = (AppSystem) req.getSession().getAttribute(SystemKey.RM.name());
        try {
            Entity<Build> buildEntity = rmSystem.getCommandBus().submit(new CreateBuildCommand(productId, buildName, patchName)).getResult();
            System.out.println("added "+buildEntity);
            resp.sendRedirect(req.getContextPath()+"/rm/Home");
        } catch (Exception e) {
            throw new ServletException(e);
        }
        
    }
}
