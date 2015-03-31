<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.Set" %>
<%@ page import="com.amazon.infra.domain.Entity" %>
<%@ page import="com.amazon.core.pm.domain.entity.Product" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Product</title>
</head>
<body>

<h2>Product</h2>
<table border="1">
<thead>
<tr>
	<td>Field</td>
	<td>Value</td>
</tr>
</thead>
<tbody>
			<% 	
 	Entity<Product> productInfo = (Entity<Product>)session.getAttribute("productInfo"); 
	if(productInfo!=null)
	{
	        %>	        
				<tr>
					<td>Id</td>
					<td><%=productInfo.getId()%></td>
				</tr>
				<tr>
					<td>Name</td>
					<td><%=productInfo.getData().getName()%></td>	
				</tr>
				<tr>
					<td>Description</td>
					<td><%=productInfo.getData().getDesc()%></td>	
				</tr>
			<%
	}
%>
		</tbody>
</table>
</body>
</html>