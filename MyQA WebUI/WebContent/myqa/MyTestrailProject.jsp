<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.Set" %>
<%@ page import="com.amazon.infra.domain.Entity" %>
<%@ page import="com.amazon.integration.demo.domain.entity.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>MyTestrailProject</title>
</head>
<body>
<% 	
 	Entity<MyTestrailProject> my = (Entity<MyTestrailProject>)session.getAttribute("myTestrailProjectInfo"); 
	System.out.println("myTestrailProjectInfo="+my);
	if(my!=null)
	{
%>
<h2>Current Values</h2>
<p>Id:<%=my.getId() %></p>
<p>Status:<%=my.getData().getStatus()%></p>
<p>TestrailProjectId:<%=my.getData().getTestrailProjectId()%></p>
<p>TestrailSuiteId:<%=my.getData().getTestrailSuiteId()%></p>
<p>Status:<%=my.getData().getStatus()%></p>
<h2>Update Actions</h2>
<form action="<%=request.getContextPath() %>/myqa/UpdateMyTestrailProject" method="post">
	<p>TestrailProjectId:<input type="text" name="testrailProjectId" value="<%=my.getData().getTestrailProjectId() %>"></p>
	<p>TestrailSuiteId:<input type="text" name="testrailSuiteId" value="<%=my.getData().getTestrailSuiteId() %>"></p>
	<input type="submit" value="Update" />
	<input type="hidden" name="id"  value="<%=my.getId() %>">
</from>
<%
	}
%>
</body>
</html>