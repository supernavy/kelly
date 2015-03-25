<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.Set" %>
<%@ page import="com.amazon.infra.domain.Entity" %>
<%@ page import="com.amazon.core.qa.domain.entity.*" %>
<%@ page import="com.amazon.core.pm.domain.entity.*" %>
<%@ page import="com.amazon.core.rm.domain.entity.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>QA System Home</title>
</head>
<body>
<h2>Add a new ProductQA</h2>
<form action="/qa/AddProductQA" method="post">
  	<p>Product:</p> <select name="productId">
				<%
				Set<Entity<Product>> allProductInfos = (Set<Entity<Product>>)session.getAttribute("allProductInfos"); 
				if(allProductInfos!=null)
				{
				    for(Entity<Product> p: allProductInfos)
				    {
				%>
				  <option value ="<%=p.getId()%>" selected="selected"><%=p.getData().getName() %></option>
				<% 
				    }
				}
				%>
				</select>
  	
  	<input type="submit" value="Submit" />
</form>

<h2>All ProductQA</h2>
<table border="1">
<thead>
<tr>
	<td>Id</td>
	<td>Status</td>
	<td>Product</td>
	<td>Plans</td>
	<td></td>
</tr>
</thead>
<tbody>
<% 	
	Set<Entity<ProductQA>> allProductQAInfos = (Set<Entity<ProductQA>>)session.getAttribute("allProductQAInfos"); 
	if(allProductQAInfos!=null)
	{
	    for(Entity<ProductQA> p: allProductQAInfos)
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
	        	<a href="/pm/Product?productId=<%=p.getData().getProductInfo().getId()%>"><%=p.getData().getProductInfo().getData().getName()%></a>
	        	</td>
	        	<td>
	        	<%=p.getData().getPlans()%>
	        	</td>
	        	<td>
	        		<form action="/qa/DeleteProductQA" method="post">
	        			<input type="hidden" name="productQAId" value="<%=p.getId()%>"/>
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

<h2>Add a new BuildQA</h2>
<form action="/qa/AddBuildQA" method="post">
	<p>Name: <input type="text" name="addBuildQA.name" /></p>
  	<p>ProductQA:</p> <select name="productQAId">
				<% 
				if(allProductQAInfos!=null)
				{
				    for(Entity<ProductQA> p: allProductQAInfos)
				    {
				%>
				  <option value ="<%=p.getId()%>" selected="selected"><%=p.getData().getName() %></option>
				<% 
				    }
				}
				%>
				</select>
  	<p>Build:</p> <select name="buildId">
				<%
				Set<Entity<Build>> allBuildInfos = (Set<Entity<Build>>)session.getAttribute("allBuildInfos"); 
				if(allBuildInfos!=null)
				{
				    for(Entity<Build> p: allBuildInfos)
				    {
				%>
				  <option value ="<%=p.getId()%>" selected="selected"><%=p.getData().getName() %></option>
				<% 
				    }
				}
				%>
				</select>
  	<input type="submit" value="Submit" />
</form>

<h2>All BuildQA</h2>
<table border="1">
<thead>
<tr>
	<td>Id</td>
	<td>ProductQA</td>
	<td>Build</td>
	<td></td>
</tr>
</thead>
<tbody>
<% 	
	Set<Entity<BuildQA>> allBuildQAInfos = (Set<Entity<BuildQA>>)session.getAttribute("allBuildQAInfos"); 
	if(allBuildQAInfos!=null)
	{
	    for(Entity<BuildQA> p: allBuildQAInfos)
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
	        	<%=p.getData().getBuildInfo().getData().getName()%>
	        	</td>
	        	<td>
	        		<form action="/qa/DeleteBuildQA" method="post">
	        			<input name="buildQAId" type="hidden" value="<%=p.getId()%>"/>
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
</body>
</html>