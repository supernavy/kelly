package com.amazon.webui.servlet.myqa;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.amazon.infra.domain.Entity;
import com.amazon.infra.system.AppSystem;
import com.amazon.integration.demo.command.MyTestrailPlanGetCommand;
import com.amazon.integration.demo.domain.entity.MyTestrailPlan;
import com.amazon.webui.servlet.SystemKey;

public class MyTestrailPlanServlet extends HttpServlet
{
 
    /**
     * TODO
     */
    private static final long serialVersionUID = 3060177957670274177L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        AppSystem myQASystem = (AppSystem) req.getSession().getAttribute(SystemKey.MyQA.name());        
        String myTestrailPlanId = req.getParameter("id");
        
        try {
            if(myTestrailPlanId!=null)
            {
                Entity<MyTestrailPlan> myTestrailPlanInfo= myQASystem.getCommandBus().submit(new MyTestrailPlanGetCommand(myTestrailPlanId)).getResult();
                req.getSession().setAttribute("myTestrailPlanInfo", myTestrailPlanInfo);
            }
            
            resp.sendRedirect(req.getContextPath()+"/myqa/MyTestrailPlan.jsp");
        } catch (Exception e) {
            throw new ServletException(e);
        }  
    }
}
