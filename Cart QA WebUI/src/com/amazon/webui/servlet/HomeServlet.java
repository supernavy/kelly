package com.amazon.webui.servlet;

import java.io.IOException;
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
import com.amazon.extension.testrail.system.SimpleTestrailSystem;
import com.amazon.extension.testrail.system.TestrailSystemAssembler;
import com.amazon.infra.system.AppSystem;
import com.amazon.infra.system.AppSystem.Layer;
import com.amazon.infra.system.AppSystemException;
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
        req.getSession().setAttribute(SystemKey.Demo.name(), demoSystem);
        resp.sendRedirect(req.getContextPath()+"/Home.jsp");
    }
    
    AppSystem rmSystem;
    AppSystem pmSystem;
    AppSystem qaSystem;
    AppSystem testrailSystem;
    AppSystem demoSystem;
    
    String url = "https://rcx-testrail.amazon.com/api.php?/api/v2";
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
        new TestrailSystemAssembler(url, username, password).assemble(testrailSystem);
        demoSystem = new SimpleDemoSystem("demo integration", Layer.Extension, qaSystem, testrailSystem, rmSystem, pmSystem);
        new DemoSystemAssembler().assemble(demoSystem);
        
        pmSystem.start();
        rmSystem.start();
        qaSystem.start();
        testrailSystem.start();
        demoSystem.start();
    }
    
    @Override
    public void destroy()
    {
        try {
            demoSystem.shutdown();
            testrailSystem.shutdown();
            qaSystem.shutdown();
            rmSystem.shutdown();
            pmSystem.shutdown();
        } catch (AppSystemException e) {
            e.printStackTrace();
        }
    }
}
