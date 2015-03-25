<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.Set" %>
<%@ page import="com.amazon.infra.domain.Entity" %>
<%@ page import="com.amazon.integration.demo.domain.entity.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Demo System Home</title>
</head>
<body>

<h2>All IntegProductQA</h2>
<table border="1">
<thead>
<tr>
	<td>Id</td>
	<td>ProductQA</td>
	<td>Status</td>
	<td>IntegTestrailProject</td>
</tr>
</thead>
<tbody>
<% 	
 	Set<Entity<IntegProductQA>> allIntegProjectInfos = (Set<Entity<IntegProductQA>>)session.getAttribute("allIntegProductQAInfos"); 
	if(allIntegProjectInfos!=null)
	{
	    for(Entity<IntegProductQA> p: allIntegProjectInfos)
	    {
	        %>
	        <tr>
	        	<td>
	        	<%=p.getId()%>
	        	</td>
	        	<td>
	        	<%=p.getData().getProductQAInfo().getData().getName()%>
	        	</td>
	        	<td>
	        	<%=p.getData().getStatus()%>
	        	</td>
	        	<td>
	        	<%=p.getData().getIntegTestrailProjectInfo()%>
	        	</td>
	    	</tr>
	    	<%
	    }
	}
%>
</tbody>
</table>

<h2>All IntegBuildQA</h2>
<table border="1">
<thead>
<tr>
	<td>Id</td>
	<td>BuildQA</td>
	<td>IntegProductQA</td>
	<td>IntegTestrailPlan</td>
	<td>Status</td>
</tr>
</thead>
<tbody>
<% 	
 	Set<Entity<IntegBuildQA>> allIntegBuildInfos = (Set<Entity<IntegBuildQA>>)session.getAttribute("allIntegBuildQAInfos"); 
	if(allIntegBuildInfos!=null)
	{
	    for(Entity<IntegBuildQA> p: allIntegBuildInfos)
	    {
	        %>
	        <tr>
	        	<td>
	        	<%=p.getId()%>
	        	</td>
	        	<td>
	        	<%=p.getData().getBuildQAInfo().getData().getName()%>
	        	</td>
	        	<td>
	        	<%=p.getData().getIntegProductQAInfo().getData().getName()%>
	        	</td>
	        	<td>
	        	<%=p.getData().getIntegTestrailPlanInfo()%>
	        	</td>
	        	<td>
	        	<%=p.getData().getStatus()%>
	        	</td>
	    	</tr>
	    	<%
	    }
	}
%>
</tbody>
</table>

<h2>All IntegTestrailProject</h2>
<table border="1">
<thead>
<tr>
	<td>Id</td>
	<td>IntegProductQA</td>
	<td>TestrailProjectId</td>
	<td>TestrailSuiteId</td>
	<td>Status</td>
</tr>
</thead>
<tbody>
<% 	
 	Set<Entity<IntegTestrailProject>> allIntegTestrailProjectInfos = (Set<Entity<IntegTestrailProject>>)session.getAttribute("allIntegTestrailProjectInfos"); 
	if(allIntegTestrailProjectInfos!=null)
	{
	    for(Entity<IntegTestrailProject> p: allIntegTestrailProjectInfos)
	    {
	        %>
	        <tr>
	        	<td>
	        	<%=p.getId()%>
	        	</td>
	        	<td>
	        	<%=p.getData().getIntegProductQAInfo().getData().getName()%>
	        	</td>
	        	<td>
	        	<%=p.getData().getTestrailProjectId()%>
	        	</td>
	        	<td>
	        	<%=p.getData().getTestrailSuiteId()%>
	        	</td>
	        	<td>
	        	<%=p.getData().getStatus()%>
	        	</td>
	    	</tr>
	    	<%
	    }
	}
%>
</tbody>
</table>

<h2>All IntegTestrailPlan</h2>
<table border="1">
<thead>
<tr>
	<td>Id</td>
	<td>IntegBuildQA</td>
	<td>TestrailPlanId</td>
	<td>Status</td>
</tr>
</thead>
<tbody>
<% 	
 	Set<Entity<IntegTestrailPlan>> allIntegTestrailPlanInfos = (Set<Entity<IntegTestrailPlan>>)session.getAttribute("allIntegTestrailPlanInfos"); 
	if(allIntegTestrailPlanInfos!=null)
	{
	    for(Entity<IntegTestrailPlan> p: allIntegTestrailPlanInfos)
	    {
	        %>
	        <tr>
	        	<td>
	        	<%=p.getId()%>
	        	</td>
	        	<td>
	        	<%=p.getData().getIntegBuildQAInfo().getData().getName()%>
	        	</td>
	        	<td>
	        	<%=p.getData().getTestrailPlanId()%>
	        	</td>
	        	<td>
	        	<%=p.getData().getStatus()%>
	        	</td>
	    	</tr>
	    	<%
	    }
	}
%>
</tbody>
</table>

<h2>All ExternalSignoff</h2>
<table border="1">
<thead>
<tr>
	<td>Id</td>
	<td>Feature</td>
	<td>IntegBuildQA</td>
	<td>Owner</td>
	<td>Status</td>
	<td>Result</td>
</tr>
</thead>
<tbody>
<%
 	Set<Entity<ExternalSignoff>> allExternalSignoffInfos = (Set<Entity<ExternalSignoff>>)session.getAttribute("allExternalSignoffInfos"); 
	if(allExternalSignoffInfos!=null)
	{
	    for(Entity<ExternalSignoff> p: allExternalSignoffInfos)
	    {
	        %>
	        <tr>
	        	<td>
	        	<%=p.getId()%>
	        	</td>
	        	<td>
	        	<%=p.getData().getFeatureName()%>
	        	</td>
	        	<td>
	        	<%=p.getData().getIntegBuildQAInfo().getData().getName()%>
	        	</td>
	        	<td>
	        	<%=p.getData().getOwner()%>
	        	</td>
	        	<td>
	        	<%=p.getData().getStatus()%>
	        	</td>
	        	<td>
	        	<%=p.getData().getResult()%>
	        	</td>
	    	</tr>
	    	<%
	    }
	}
%>
</tbody>
</table>
</body>
</html>