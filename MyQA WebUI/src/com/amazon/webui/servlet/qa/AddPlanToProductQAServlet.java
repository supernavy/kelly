package com.amazon.webui.servlet.qa;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.amazon.core.qa.command.ProductQAGetCommand;
import com.amazon.core.qa.command.ProductQAUpdateCommand;
import com.amazon.core.qa.domain.entity.ProductQA;
import com.amazon.core.qa.domain.vo.productqa.Plan;
import com.amazon.infra.domain.Entity;
import com.amazon.infra.system.AppSystem;
import com.amazon.webui.servlet.SystemKey;

public class AddPlanToProductQAServlet extends HttpServlet
{
 
    /**
     * TODO
     */
    private static final long serialVersionUID = 3060177957670274177L;
    
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        AppSystem qaSystem = (AppSystem) req.getSession().getAttribute(SystemKey.QA.name());  
        String productQAId = (String) req.getSession().getAttribute("productQAId");
        
        if(productQAId !=null)
        {
            String planName = req.getParameter("planName");
            String entryBaseName = req.getParameter("entryBaseName");
            Plan plan = new Plan(planName, entryBaseName);
            try {
                Entity<ProductQA> productQAInfo = qaSystem.getCommandBus().submit(new ProductQAGetCommand(productQAId)).getResult();
                ProductQA productQA = productQAInfo.getData().addPlan(plan);
                productQAInfo = qaSystem.getCommandBus().submit(new ProductQAUpdateCommand(productQAId, productQA)).getResult();
                req.getSession().setAttribute("productQAInfo", productQAInfo);        
            } catch (Exception e) {
                throw new ServletException(e);
            }
        }
        
        resp.sendRedirect(req.getContextPath()+"/qa/ProductQA.jsp");
    }
}
