package com.amazon.webui.servlet.demo;

import java.io.IOException;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.amazon.infra.domain.Entity;
import com.amazon.infra.system.AppSystem;
import com.amazon.integration.demo.command.ExternalSignoffGetAllCommand;
import com.amazon.integration.demo.command.IntegBuildQAGetAllCommand;
import com.amazon.integration.demo.command.IntegProductQAGetAllCommand;
import com.amazon.integration.demo.command.IntegTestrailPlanGetAllCommand;
import com.amazon.integration.demo.command.IntegTestrailProjectGetAllCommand;
import com.amazon.integration.demo.domain.entity.ExternalSignoff;
import com.amazon.integration.demo.domain.entity.IntegBuildQA;
import com.amazon.integration.demo.domain.entity.IntegProductQA;
import com.amazon.integration.demo.domain.entity.IntegTestrailPlan;
import com.amazon.integration.demo.domain.entity.IntegTestrailProject;
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
        AppSystem demoSystem = (AppSystem) req.getSession().getAttribute(SystemKey.Demo.name());        
        
        try {
            Set<Entity<IntegProductQA>> allIntegProductQAInfos = demoSystem.getCommandBus().submit(new IntegProductQAGetAllCommand()).getResult();
            req.getSession().setAttribute("allIntegProductQAInfos", allIntegProductQAInfos);
            
            Set<Entity<IntegBuildQA>> allIntegBuildQAInfos = demoSystem.getCommandBus().submit(new IntegBuildQAGetAllCommand()).getResult();
            req.getSession().setAttribute("allIntegBuildQAInfos", allIntegBuildQAInfos);
            
            Set<Entity<IntegTestrailProject>> allIntegTestrailProjectInfos = demoSystem.getCommandBus().submit(new IntegTestrailProjectGetAllCommand()).getResult();
            req.getSession().setAttribute("allIntegTestrailProjectInfos", allIntegTestrailProjectInfos);
            
            Set<Entity<IntegTestrailPlan>> allIntegTestrailPlanInfos = demoSystem.getCommandBus().submit(new IntegTestrailPlanGetAllCommand()).getResult();
            req.getSession().setAttribute("allIntegTestrailPlanInfos", allIntegTestrailPlanInfos);
            
            Set<Entity<ExternalSignoff>> allExternalSignoffInfos = demoSystem.getCommandBus().submit(new ExternalSignoffGetAllCommand()).getResult();
            req.getSession().setAttribute("allExternalSignoffInfos", allExternalSignoffInfos);
            resp.sendRedirect(req.getContextPath()+"/demo/Home.jsp");
        } catch (Exception e) {
            throw new ServletException(e);
        }  
    }
}
