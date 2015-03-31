<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.Set" %>
<%@ page import="com.amazon.infra.domain.Entity" %>
<%@ page import="com.amazon.infra.commandbus.*" %>
<%@ page import="com.amazon.infra.eventbus.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Bus</title>
</head>
<body>

<h2>Commands</h2>
<table border="1">
<thead>
<tr>
	<td>Id</td>
	<td>Status</td>
	<td>SubmitTime</td>
	<td>Class</td>
	<td>Result</td>
</tr>
</thead>
<tbody>
<% 	
 	Set<Entity<CommandExecution>> allCommandExecutionInfos = (Set<Entity<CommandExecution>>)session.getAttribute("allCommandExecutionInfos"); 
	if(allCommandExecutionInfos!=null)
	{
	    for(Entity<CommandExecution> p: allCommandExecutionInfos)
	    {
	        %>
	        <tr>
	        	<td>
	        	<%=p.getId()%>
	        	</td>
	        	<td>
	        	<%=p.getData().getStatus()%>
	        	</td>
	        	<td>
	        	<%=p.getData().getSubmitTime()%>
	        	</td>
	        	<td>
	        	<%=p.getData().getCommand()%>
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

<h2>Emitted Events</h2>
<table border="1">
<thead>
<tr>
	<td>Id</td>
	<td>HappenTime</td>
	<td>Status</td>
	<td>Event</td>
	<td>Remaining Handler Total</td>
	<td>Succeed Handler Total</td>
	<td>Fail Handler Total</td>
</tr>
</thead>
<tbody>
<% 	
	Set<Entity<EventDistribution<? extends Event>>> allEventDistributionInfos = (Set<Entity<EventDistribution<? extends Event>>>)session.getAttribute("allEventDistributionInfos"); 
	if(allEventDistributionInfos!=null)
	{
	    for(Entity<EventDistribution<? extends Event>> p: allEventDistributionInfos)
	    {
	        %>
	        <tr>
	        	<td>
	        	<%=p.getId()%>
	        	</td>
	        	<td>
	        	<%=p.getData().getHappenTime()%>
	        	</td>
	        	<td>
	        	<%=p.getData().getStatus()%>
	        	</td>
	        	<td>
	        	<%=p.getData().getEvent()%>
	        	</td>
	        	<td>
	        	<%=p.getData().getSubDistributions().size()%>
	        	</td>
	        	<td>
	        	<%=p.getData().getDoneSubDistributions().size()%>
	        	</td>
	        	<td>
	        	<%=p.getData().getErrorSubDistributions().size()%>
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