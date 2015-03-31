<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Set" %>
<%@ page import="com.amazon.infra.domain.Entity" %>
<%@ page import="com.amazon.core.qa.domain.entity.*" %>
<%@ page import="com.amazon.core.qa.domain.vo.common.*" %>
<%@ page import="com.amazon.core.qa.domain.vo.productqa.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Product QA</title>
</head>
<body>
<h2>Product QA</h2>
<table border="1">
<thead>
<tr>
	<td>Field</td>
	<td>Value</td>
</tr>
</thead>
<tbody>
			<% 	
 	Entity<ProductQA> productQAInfo = (Entity<ProductQA>)session.getAttribute("productQAInfo"); 
	if(productQAInfo!=null)
	{
	        %>	        
				<tr>
					<td>Id</td>
					<td><%=productQAInfo.getId()%></td>
				</tr>
				<tr>
					<td>Name</td>
					<td><%=productQAInfo.getData().getName()%></td>	
				</tr>
				<tr>
					<td>ProductInfo</td>
					<td><%=productQAInfo.getData().getProductInfo()%></td>	
				</tr>
				<tr>
					<td>ProductInfo</td>
					<td><%=productQAInfo.getData().getStatus()%></td>	
				</tr>
				<tr>
					<td>Plans</td>
					<td>
						<% 	Map<String,Plan> plans = productQAInfo.getData().getPlans();
							for(Plan p: plans.values())
							{
						%>
							<h3>Plan: <%=p.getName() %></h3>
							<p>Entry Base Name: <%=p.getEntryBaseName() %></p>
							<p>Entries:</p>
							<table border="1">
								<thead>
									<tr>
										<td>Name</td>
										<td>Browser</td>
										<td>Locale</td>
										<td>Platform</td>
										<td>Priority</td>
										<td>View</td>
									</tr>
								</thead>
							<% 	Map<String,PlanEntry> planEntries = p.getPlanEntries();
							for(PlanEntry e: planEntries.values())
							{
							%>
								<tr>
									<td><%=e.getName()%></td>
									<td><%=e.getBrowser()%></td>
									<td><%=e.getLocale()%></td>
									<td><%=e.getPlatform()%></td>
									<td><%=e.getPriority()%></td>
									<td><%=e.getView()%></td>
								</tr>						
							<%
							}
							%>
							</table>
						<%
							}
						%>
						
					</td>	
				</tr>
			<%
	}
%>
		</tbody>
</table>

<h2>Add Plan</h2>
<% if(productQAInfo!=null) 
	{
%>
<form action="<%=request.getContextPath() %>/qa/AddPlanToProductQA" method="post">
	<p>Plan Name:<input type="text" name="planName" /></p>
	<p>Entry Base Name:<input type="text" name="entryBaseName" /></p>
	<input type="submit" name="add plan" value="Add Plan" />
	<input type="hidden" name="id"  value="<%=productQAInfo.getId() %>" />
</form>
<% 
	}
%>

<% if(productQAInfo!=null) 
	{
%>
<h2>Add Plan Entry</h2>
<form id="b" name="b" action="<%=request.getContextPath() %>/qa/AddPlanEntryToProductQA" method="post">
	<p>Plan Name: <select name="planName">
				<%
				Set<String> allPlanNames = productQAInfo.getData().getPlans().keySet(); 
				if(allPlanNames!=null)
				{
				    for(String planName: allPlanNames)
				    {
				%>
				  <option value ="<%=planName%>" selected="selected"><%=planName %></option>
				<% 
				    }
				}
				%>
				</select>
	</p>
	<p>Name: <input type="text" name="planEntryName"/></p>
	<p>Locale: <select name="planEntryLocale">
				<%
				Locale[] allLocales = Locale.values(); 
				if(allLocales!=null)
				{
				    for(Locale l: allLocales)
				    {
				%>
				  <option value ="<%=l.name()%>" selected="selected"><%=l.name() %></option>
				<% 
				    }
				}
				%>
				</select>
	</p>
    <p>Platform: <select name="planEntryPlatform">
				<%
				Platform[] allPlatforms = Platform.values(); 
				if(allPlatforms!=null)
				{
				    for(Platform p: allPlatforms)
				    {
				%>
				  <option value ="<%=p.name()%>" selected="selected"><%=p.name() %></option>
				<% 
				    }
				}
				%>
				</select>
	</p>
    <p>Browser: <select name="planEntryBrowser">
				<%
				Browser[] allBrowsers = Browser.values(); 
				if(allBrowsers!=null)
				{
				    for(Browser b: allBrowsers)
				    {
				%>
				  <option value ="<%=b.name()%>" selected="selected"><%=b.name() %></option>
				<% 
				    }
				}
				%>
				</select>
	</p>
    <p>Priority: <select name="planEntryPriority">
				<%
				Priority[] allPriorities = Priority.values(); 
				if(allPriorities!=null)
				{
				    for(Priority p: allPriorities)
				    {
				%>
				  <option value ="<%=p.name()%>" selected="selected"><%=p.name() %></option>
				<% 
				    }
				}
				%>
				</select>
	</p>
    <p>View: <select name="planEntryView">
				<%
				View[] allViews = View.values(); 
				if(allViews!=null)
				{
				    for(View v: allViews)
				    {
				%>
				  <option value ="<%=v.name()%>" selected="selected"><%=v.name() %></option>
				<% 
				    }
				}
				%>
				</select>
	</p>
	<input type="submit" name="add plan entry" value="Add Plan Entry" />
	<input type="hidden" name="id"  value="<%=productQAInfo.getId() %>" />
</form>
<% 
	}
%>
</body>
</html>