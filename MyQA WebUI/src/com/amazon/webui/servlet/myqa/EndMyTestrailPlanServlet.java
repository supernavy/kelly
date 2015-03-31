package com.amazon.webui.servlet.myqa;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.amazon.infra.system.AppSystem;
import com.amazon.integration.demo.command.MyTestrailPlanEndCommand;
import com.amazon.webui.servlet.SystemKey;

public class EndMyTestrailPlanServlet extends HttpServlet
{
 
    /**
     * TODO
     */
    private static final long serialVersionUID = 3060177957670274177L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        AppSystem myQASystem = (AppSystem) req.getSession().getAttribute(SystemKey.MyQA.name());        
        String myTestrailPlanId = req.getParameter("id");
        
        try {
            if(myTestrailPlanId!=null)
            {
                myQASystem.getCommandBus().submit(new MyTestrailPlanEndCommand(myTestrailPlanId));
            }
            
            resp.sendRedirect(req.getContextPath()+"/myqa/MyTestrailPlan.jsp");
        } catch (Exception e) {
            throw new ServletException(e);
        }  
    }
}
