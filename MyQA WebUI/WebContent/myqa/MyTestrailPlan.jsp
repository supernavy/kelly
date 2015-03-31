<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.Set" %>
<%@ page import="com.amazon.infra.domain.Entity" %>
<%@ page import="com.amazon.integration.demo.domain.entity.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>MyTestrailPlan</title>
</head>
<body>
<% 	
 	Entity<MyTestrailPlan> my = (Entity<MyTestrailPlan>)session.getAttribute("myTestrailPlanInfo"); 
	if(my!=null)
	{
%>
<h2>Current Values</h2>
<p>Id:<%=my.getId() %></p>
<p><font color="red">Status:<%=my.getData().getStatus()%></font></p>
<p>TestrailPlanId:<%=my.getData().getTestrailPlanId() %></p>
<h2>Update Actions</h2>
<form action="<%=request.getContextPath() %>/myqa/UpdateMyTestrailPlan" method="post">
	<p>TestrailPlanId:<input type="text" name="testrailPlanId" value="<%=my.getData().getTestrailPlanId() %>"><input type="submit" value="Update" /></p>
	<input type="hidden" name="id"  value="<%=my.getId() %>">
</form>
<form action="<%=request.getContextPath() %>/myqa/EndMyTestrailPlan" method="post">
	<p>Mark the plan as End:<input type="submit" value="End" /></p>
	<input type="hidden" name="id"  value="<%=my.getId() %>">
</form>
<%
	}
%>
</body>
</html>