<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.Set" %>
<%@ page import="com.amazon.infra.domain.Entity" %>
<%@ page import="com.amazon.integration.demo.domain.entity.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>MyQA System Home</title>
</head>
<body>

<h2>All MyProductQA</h2>
<table border="1">
<thead>
<tr>
	<td>Id</td>
	<td>ProductQA</td>
	<td>Status</td>
	<td>MyTestrailProject</td>
</tr>
</thead>
<tbody>
<% 	
 	Set<Entity<MyProductQA>> allMyProjectInfos = (Set<Entity<MyProductQA>>)session.getAttribute("allMyProductQAInfos"); 
	if(allMyProjectInfos!=null)
	{
	    for(Entity<MyProductQA> p: allMyProjectInfos)
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
	        	<font color="red"><%=p.getData().getStatus()%></font>
	        	</td>
	        	<td>
	        	<%=p.getData().getMyTestrailProjectInfo()%>
	        	</td>
	    	</tr>
	    	<%
	    }
	}
%>
</tbody>
</table>

<h2>All MyBuildQA</h2>
<table border="1">
<thead>
<tr>
	<td>Id</td>
	<td>BuildQA</td>
	<td>MyProductQA</td>
	<td>MyTestrailPlan</td>
	<td>Status</td>
</tr>
</thead>
<tbody>
<% 	
 	Set<Entity<MyBuildQA>> allMyBuildInfos = (Set<Entity<MyBuildQA>>)session.getAttribute("allMyBuildQAInfos"); 
	if(allMyBuildInfos!=null)
	{
	    for(Entity<MyBuildQA> p: allMyBuildInfos)
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
	        	<%=p.getData().getMyProductQAInfo().getData().getName()%>
	        	</td>
	        	<td>
	        	<%=p.getData().getMyTestrailPlanInfo()%>
	        	</td>
	        	<td>
	        	<font color="red"><%=p.getData().getStatus()%></font>
	        	</td>
	    	</tr>
	    	<%
	    }
	}
%>
</tbody>
</table>

<h2>All MyTestrailProject</h2>
<table border="1">
<thead>
<tr>
	<td>Id</td>
	<td>MyProductQA</td>
	<td>TestrailProjectId</td>
	<td>TestrailSuiteId</td>
	<td>Status</td>
	<td>Action</td>
</tr>
</thead>
<tbody>
<% 	
 	Set<Entity<MyTestrailProject>> allMyTestrailProjectInfos = (Set<Entity<MyTestrailProject>>)session.getAttribute("allMyTestrailProjectInfos"); 
	if(allMyTestrailProjectInfos!=null)
	{
	    for(Entity<MyTestrailProject> p: allMyTestrailProjectInfos)
	    {
	        %>
	        <tr>
	        	<td>
	        	<%=p.getId()%>
	        	</td>
	        	<td>
	        	<%=p.getData().getMyProductQAInfo().getData().getName()%>
	        	</td>
	        	<td>
	        	<a href="<%=session.getAttribute("testrail.url")%>/testrail/index.php?/projects/overview/<%=p.getData().getTestrailProjectId()%>"><%=p.getData().getTestrailProjectId()%></a>
	        	</td>
	        	<td>
	        	<a href="<%=session.getAttribute("testrail.url")%>/testrail/index.php?/suites/view/<%=p.getData().getTestrailSuiteId()%>"><%=p.getData().getTestrailSuiteId()%></a>
	        	</td>
	        	<td>
	        	<font color="red"><%=p.getData().getStatus()%></font>
	        	</td>
	        	<td>
	        		<a href="<%=request.getContextPath() %>/myqa/MyTestrailProject?myTestrailProjectId=<%=p.getId()%>">Detail</a>
	        	</td>
	    	</tr>
	    	<%
	    }
	}
%>
</tbody>
</table>

<h2>All MyTestrailPlan</h2>
<table border="1">
<thead>
<tr>
	<td>Id</td>
	<td>MyBuildQA</td>
	<td>TestrailPlanId</td>
	<td>Status</td>
	<td>Action</td>
</tr>
</thead>
<tbody>
<% 	
 	Set<Entity<MyTestrailPlan>> allMyTestrailPlanInfos = (Set<Entity<MyTestrailPlan>>)session.getAttribute("allMyTestrailPlanInfos"); 
	if(allMyTestrailPlanInfos!=null)
	{
	    for(Entity<MyTestrailPlan> p: allMyTestrailPlanInfos)
	    {
	        %>
	        <tr>
	        	<td>
	        	<%=p.getId()%>
	        	</td>
	        	<td>
	        	<%=p.getData().getMyBuildQAInfo().getData().getName()%>
	        	</td>
	        	<td>
	        	<a href="<%=session.getAttribute("testrail.url")%>/testrail/index.php?/plans/view/<%=p.getData().getTestrailPlanId()%>"><%=p.getData().getTestrailPlanId()%></a>
	        	</td>
	        	<td>
	        	<font color="red"><%=p.getData().getStatus()%></font>
	        	</td>
	        	<td>
	        		<a href="<%=request.getContextPath() %>/myqa/MyTestrailPlan?id=<%=p.getId()%>">Detail</a>
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
	<td>MyBuildQA</td>
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
	        	<%=p.getData().getMyBuildQAInfo().getData().getName()%>
	        	</td>
	        	<td>
	        	<%=p.getData().getOwner()%>
	        	</td>
	        	<td>
	        	<font color="red"><%=p.getData().getStatus()%></font>
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

<h2><a href="../Bus">Command&Event Bus</a></h2>
</body>
</html>