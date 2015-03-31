package com.amazon.webui.servlet.testrail;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.amazon.infra.system.AppSystem;
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
        AppSystem testrailSystem = (AppSystem) req.getSession().getAttribute(SystemKey.Testrail.name());     
        
        try {
            
            req.getSession().setAttribute("system", testrailSystem);
            
            resp.sendRedirect(req.getContextPath()+"/testrail/Home.jsp");
        } catch (Exception e) {
            throw new ServletException(e);
        }        
    }
}
