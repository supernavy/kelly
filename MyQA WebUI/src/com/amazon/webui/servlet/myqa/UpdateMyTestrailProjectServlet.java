package com.amazon.webui.servlet.myqa;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.amazon.infra.domain.Entity;
import com.amazon.infra.system.AppSystem;
import com.amazon.integration.demo.command.MyTestrailProjectGetCommand;
import com.amazon.integration.demo.command.MyTestrailProjectUpdateCommand;
import com.amazon.integration.demo.domain.entity.MyTestrailProject;
import com.amazon.webui.servlet.SystemKey;

public class UpdateMyTestrailProjectServlet extends HttpServlet
{
 
    /**
     * TODO
     */
    private static final long serialVersionUID = 3060177957670274177L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        AppSystem myQASystem = (AppSystem) req.getSession().getAttribute(SystemKey.MyQA.name());        
        String myTestrailProjectId = req.getParameter("id");
        String testrailProjectId = req.getParameter("testrailProjectId");
        String testrailSuiteId = req.getParameter("testrailSuiteId");
        try {
            Entity<MyTestrailProject> myTestrailProjectInfo= myQASystem.getCommandBus().submit(new MyTestrailProjectGetCommand(myTestrailProjectId)).getResult();
            MyTestrailProject myTestrailProject= myTestrailProjectInfo.getData().updateTestrailInfo(Long.valueOf(testrailProjectId), Long.valueOf(testrailSuiteId));
            myTestrailProjectInfo = myQASystem.getCommandBus().submit(new MyTestrailProjectUpdateCommand(myTestrailProjectId, myTestrailProject)).getResult();
            req.getSession().setAttribute("myTestrailProjectInfo", myTestrailProjectInfo);
            
            resp.sendRedirect(req.getContextPath()+"/myqa/MyTestrailProject.jsp");
        } catch (Exception e) {
            throw new ServletException(e);
        }  
    }
}
