package com.amazon.webui.servlet.qa;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.amazon.core.qa.command.ProductQAGetCommand;
import com.amazon.core.qa.command.ProductQAUpdateCommand;
import com.amazon.core.qa.domain.entity.ProductQA;
import com.amazon.core.qa.domain.vo.common.Browser;
import com.amazon.core.qa.domain.vo.common.Locale;
import com.amazon.core.qa.domain.vo.common.Platform;
import com.amazon.core.qa.domain.vo.common.Priority;
import com.amazon.core.qa.domain.vo.common.View;
import com.amazon.core.qa.domain.vo.productqa.Plan;
import com.amazon.core.qa.domain.vo.productqa.PlanEntry;
import com.amazon.infra.domain.Entity;
import com.amazon.infra.system.AppSystem;
import com.amazon.webui.servlet.SystemKey;

public class AddPlanEntryToProductQAServlet extends HttpServlet
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
        String planName = req.getParameter("planName");
        if(productQAId !=null)
        {         
            if(planName!=null && !planName.equals(""))
            {            
                String planEntryName = req.getParameter("planEntryName");
                String planEntryLocale = req.getParameter("planEntryLocale");
                String planEntryPlatform = req.getParameter("planEntryPlatform");
                String planEntryBrowser = req.getParameter("planEntryBrowser");
                String planEntryView = req.getParameter("planEntryView");
                String planEntryPriority = req.getParameter("planEntryPriority");
                
                PlanEntry planEntry = new PlanEntry(planEntryName, Locale.valueOf(planEntryLocale), Platform.valueOf(planEntryPlatform), Browser.valueOf(planEntryBrowser), View.valueOf(planEntryView), Priority.valueOf(planEntryPriority));
                try {
                    Entity<ProductQA> productQAInfo = qaSystem.getCommandBus().submit(new ProductQAGetCommand(productQAId)).getResult();
                    Plan plan = productQAInfo.getData().getPlans().get(planName).addEntry(planEntry);
                    ProductQA productQA = productQAInfo.getData().updatePlan(plan);
                    productQAInfo = qaSystem.getCommandBus().submit(new ProductQAUpdateCommand(productQAId, productQA)).getResult();
                    req.getSession().setAttribute("productQAInfo", productQAInfo);        
                } catch (Exception e) {
                    throw new ServletException(e);
                }
            }
        }
        
        resp.sendRedirect(req.getContextPath()+"/qa/ProductQA.jsp");
    }
}
