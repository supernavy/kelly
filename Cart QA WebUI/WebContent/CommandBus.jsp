<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.Set" %>
<%@ page import="com.amazon.infra.domain.Entity" %>
<%@ page import="com.amazon.infra.commandbus.*" %>

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
</body>
</html>