<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.Set" %>
<%@ page import="com.amazon.infra.domain.Entity" %>
<%@ page import="com.amazon.core.pm.domain.entity.Product" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>PM System Home</title>
</head>
<body>
<h2>Add a new product</h2>
<form action="AddProduct" method="post">
	<p>Name: <input type="text" name="addProduct.name" /></p>
  	<p>Description: <input type="text" name="addProduct.desc" /></p>
  	<input type="submit" value="Submit" />
</form>

<h2>All Products</h2>
<table border="1">
<thead>
<tr>
	<td>Id</td>
	<td>Name</td>
	<td>Description</td>
	<td></td>
</tr>
</thead>
<tbody>
<% 	
 	Set<Entity<Product>> allProductInfos = (Set<Entity<Product>>)session.getAttribute("allProductInfos"); 
	if(allProductInfos!=null)
	{
	    for(Entity<Product> p: allProductInfos)
	    {
	        %>
	        <tr>
	        	<td>
	        	<%=p.getId()%>
	        	</td>
	        	<td>
	        	<a href="/pm/Product?productId=<%=p.getId()%>"><%=p.getData().getName()%></a>
	        	</td>
	        	<td>
	        	<%=p.getData().getDesc()%>
	        	</td>
	        	<td>
	        		<form action="DeleteProduct" method="post">
	        			<input type="hidden" name="deleteProduct.id" value="<%=p.getId()%>"/>
	        			<input type="submit" value="Delete"/>
	        		</form>
	        	</td>
	    	</tr>
	    	<%
	    }
	}
%>
</tbody>
</table>
<h2><a href="../CommandBus">Command Bus</a></h2>
</body>
</html>