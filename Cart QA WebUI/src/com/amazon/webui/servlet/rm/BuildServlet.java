package com.amazon.webui.servlet.rm;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.amazon.core.rm.command.GetBuildCommand;
import com.amazon.core.rm.domain.entity.Build;
import com.amazon.infra.domain.Entity;
import com.amazon.infra.system.AppSystem;
import com.amazon.webui.servlet.SystemKey;

public class BuildServlet extends HttpServlet
{
 
    /**
     * TODO
     */
    private static final long serialVersionUID = 3060177957670274177L;
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        AppSystem rmSystem = (AppSystem) req.getSession().getAttribute(SystemKey.RM.name());        
        String buildId = req.getParameter("buildId");
        if(buildId!=null)
        {
            req.getSession().setAttribute("buildId", buildId);
        }
        buildId = (String) req.getSession().getAttribute("buildId");
        try {
            if(buildId!=null)
            {
                Entity<Build> buildInfo = rmSystem.getCommandBus().submit(new GetBuildCommand(buildId)).getResult();
                req.getSession().setAttribute("buildInfo", buildInfo);
                resp.sendRedirect(req.getContextPath()+"/rm/Build.jsp");
            } 
            resp.sendError(400, "no build id");
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
