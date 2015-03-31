package com.amazon.webui.servlet;

import java.io.IOException;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.amazon.infra.commandbus.CommandExecution;
import com.amazon.infra.domain.Entity;
import com.amazon.infra.eventbus.Event;
import com.amazon.infra.eventbus.EventDistribution;
import com.amazon.infra.system.AppSystem;

public class BusServlet extends HttpServlet
{    
    /**
     * TODO
     */
    private static final long serialVersionUID = 3060177957670274177L;
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        AppSystem appSystem = (AppSystem) req.getSession().getAttribute("system");        
        System.out.println("appSystem==="+appSystem);
        try {
            Set<Entity<CommandExecution<?>>> allCommandExecutionInfos = appSystem.getCommandBus().findAllCommandExecutions();
            req.getSession().setAttribute("allCommandExecutionInfos", allCommandExecutionInfos);
            
            Set<Entity<EventDistribution<? extends Event>>> allEventDistributionInfos = appSystem.getEventBus().findAllEvents();
            req.getSession().setAttribute("allEventDistributionInfos", allEventDistributionInfos);
            
            resp.sendRedirect(req.getContextPath()+"/Bus.jsp");
        } catch (Exception e) {
            throw new ServletException(e);
        }        
    }
}
