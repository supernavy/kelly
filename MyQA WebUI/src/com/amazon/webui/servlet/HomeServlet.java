package com.amazon.webui.servlet;

import java.io.IOException;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.amazon.core.pm.system.PMSystemAssembler;
import com.amazon.core.pm.system.SimplePMSystem;
import com.amazon.core.qa.system.QASystemAssembler;
import com.amazon.core.qa.system.SimpleQASystem;
import com.amazon.core.rm.system.RMSystemAssembler;
import com.amazon.core.rm.system.SimpleRMSystem;
import com.amazon.extension.testrail.command.DeleteProjectCommand;
import com.amazon.extension.testrail.system.SimpleTestrailSystem;
import com.amazon.extension.testrail.system.TestrailSystemAssembler;
import com.amazon.infra.commandbus.CommandBusException;
import com.amazon.infra.commandbus.CommandException;
import com.amazon.infra.domain.Entity;
import com.amazon.infra.system.AppSystem;
import com.amazon.infra.system.AppSystem.Layer;
import com.amazon.integration.demo.command.MyTestrailProjectGetAllCommand;
import com.amazon.integration.demo.domain.entity.MyTestrailProject;
import com.amazon.integration.demo.system.DemoSystemAssembler;
import com.amazon.integration.demo.system.SimpleDemoSystem;

public class HomeServlet extends HttpServlet
{

    /**
     * TODO
     */
    private static final long serialVersionUID = -6361109269870222509L;
    
    @Override
    public void init() throws ServletException
    {
        super.init();
        try {
            myInit();
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        req.getSession().setAttribute(SystemKey.PM.name(), pmSystem);
        req.getSession().setAttribute(SystemKey.RM.name(), rmSystem);
        req.getSession().setAttribute(SystemKey.QA.name(), qaSystem);
        req.getSession().setAttribute(SystemKey.Testrail.name(), testrailSystem);
        req.getSession().setAttribute(SystemKey.MyQA.name(), demoSystem);
        req.getSession().setAttribute("testrail.url", url);
        resp.sendRedirect(req.getContextPath()+"/Home.jsp");
    }
    
    AppSystem rmSystem;
    AppSystem pmSystem;
    AppSystem qaSystem;
    AppSystem testrailSystem;
    AppSystem demoSystem;
    
    String url = "https://testrail-test.amazon.com:20202";
    String username = "supernavy_trash@sina.com";
    String password = "Test123";
    
    private void myInit() throws Exception 
    {
        pmSystem = new SimplePMSystem("demo product", Layer.Core);
        new PMSystemAssembler().assemble(pmSystem);       
        rmSystem = new SimpleRMSystem("demo build", Layer.Core, pmSystem);
        new RMSystemAssembler().assemble(rmSystem);
        qaSystem = new SimpleQASystem("demo qa", Layer.Core, pmSystem, rmSystem);
        new QASystemAssembler().assemble(qaSystem);
        testrailSystem = new SimpleTestrailSystem("demo testrail system", Layer.Extension);
        new TestrailSystemAssembler(url+"/api.php?/api/v2", username, password).assemble(testrailSystem);
        demoSystem = new SimpleDemoSystem("demo integration", Layer.Extension, qaSystem, testrailSystem, rmSystem, pmSystem);
        new DemoSystemAssembler().assemble(demoSystem);
        
        pmSystem.start();
        rmSystem.start();
        qaSystem.start();
        testrailSystem.start();
        demoSystem.start();
    }
    
    private void myCleanUp() throws CommandException, CommandBusException {        
        Set<Entity<MyTestrailProject>> allMyTestrailProjectInfos = demoSystem.getCommandBus().submit(new MyTestrailProjectGetAllCommand()).getResult();
        for(Entity<MyTestrailProject> project: allMyTestrailProjectInfos)
        {
            if(project.getData().getTestrailProjectId()!=null)
            {
                testrailSystem.getCommandBus().submit(new DeleteProjectCommand(project.getData().getTestrailProjectId()));
            }
        }
        
    }
    
    @Override
    public void destroy()
    {
        try {
            myCleanUp();
            demoSystem.shutdown();
            testrailSystem.shutdown();
            qaSystem.shutdown();
            rmSystem.shutdown();
            pmSystem.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
