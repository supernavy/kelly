package com.amazon.webui.servlet.myqa;

import java.io.IOException;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.amazon.infra.domain.Entity;
import com.amazon.infra.system.AppSystem;
import com.amazon.integration.demo.command.ExternalSignoffGetAllCommand;
import com.amazon.integration.demo.command.MyBuildQAGetAllCommand;
import com.amazon.integration.demo.command.MyProductQAGetAllCommand;
import com.amazon.integration.demo.command.MyTestrailPlanGetAllCommand;
import com.amazon.integration.demo.command.MyTestrailProjectGetAllCommand;
import com.amazon.integration.demo.domain.entity.ExternalSignoff;
import com.amazon.integration.demo.domain.entity.MyBuildQA;
import com.amazon.integration.demo.domain.entity.MyProductQA;
import com.amazon.integration.demo.domain.entity.MyTestrailPlan;
import com.amazon.integration.demo.domain.entity.MyTestrailProject;
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
        AppSystem demoSystem = (AppSystem) req.getSession().getAttribute(SystemKey.MyQA.name());        
        
        try {
            Set<Entity<MyProductQA>> allMyProductQAInfos = demoSystem.getCommandBus().submit(new MyProductQAGetAllCommand()).getResult();
            req.getSession().setAttribute("allMyProductQAInfos", allMyProductQAInfos);
            
            Set<Entity<MyBuildQA>> allMyBuildQAInfos = demoSystem.getCommandBus().submit(new MyBuildQAGetAllCommand()).getResult();
            req.getSession().setAttribute("allMyBuildQAInfos", allMyBuildQAInfos);
            
            Set<Entity<MyTestrailProject>> allMyTestrailProjectInfos = demoSystem.getCommandBus().submit(new MyTestrailProjectGetAllCommand()).getResult();
            req.getSession().setAttribute("allMyTestrailProjectInfos", allMyTestrailProjectInfos);
            
            Set<Entity<MyTestrailPlan>> allMyTestrailPlanInfos = demoSystem.getCommandBus().submit(new MyTestrailPlanGetAllCommand()).getResult();
            req.getSession().setAttribute("allMyTestrailPlanInfos", allMyTestrailPlanInfos);
            
            Set<Entity<ExternalSignoff>> allExternalSignoffInfos = demoSystem.getCommandBus().submit(new ExternalSignoffGetAllCommand()).getResult();
            req.getSession().setAttribute("allExternalSignoffInfos", allExternalSignoffInfos);
            
            req.getSession().setAttribute("system", demoSystem);
            
            resp.sendRedirect(req.getContextPath()+"/myqa/Home.jsp");
        } catch (Exception e) {
            throw new ServletException(e);
        }  
    }
}
