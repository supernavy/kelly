<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.Set" %>
<%@ page import="com.amazon.infra.domain.Entity" %>
<%@ page import="com.amazon.core.rm.domain.entity.Build" %>
<%@ page import="com.amazon.core.pm.domain.entity.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>RM System Home</title>
</head>
<body>
This is RM System
</body>
<h2>Add a new build</h2>
<form action="AddBuild" method="post">
	<p>Product: <select name="productId">
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
	</p>
  	<p>Base Name: <input type="text" name="baseName" /></p>
  	<p>Patch Name: <input type="text" name="patchName" value=""/></p>
  	<input type="submit" value="Submit" />
</form>

<h2>All Builds</h2>
<table border="1">
<thead>
<tr>
	<td>Id</td>
	<td>Name</td>
	<td>PatchName</td>
	<td>Product</td>
	<td></td>
</tr>
</thead>
<tbody>
<% 	
	Set<Entity<Build>> allBuildInfos = (Set<Entity<Build>>)session.getAttribute("allBuildInfos"); 
	if(allBuildInfos!=null)
	{
	    for(Entity<Build> p: allBuildInfos)
	    {
	        System.out.println("p.getData()="+p.getData());
	        System.out.println("p.getData().getPatchName()="+p.getData().getPatchName());
	        %>
	        <tr>
	        	<td>
	        	<%=p.getId()%>
	        	</td>
	        	<td>
	        	<%=p.getData().getBuildName()%>
	        	</td>
	        	<td>
	        	<%=p.getData().getPatchName()%>
	        	</td>
	        	<td>
	        		<a href="/pm/Product?productId=<%=p.getData().getProductInfo().getId()%>"><%=p.getData().getProductInfo().getData().getName()%></a>
	        	</td>
	        	<td>
	        		<form action="DeleteBuild" method="post">
	        			<input name="deleteBuild.id" value="<%=p.getId()%>"/>
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
<h2><a href="../Bus">Command&Event Bus</a></h2>
</body>
</html>