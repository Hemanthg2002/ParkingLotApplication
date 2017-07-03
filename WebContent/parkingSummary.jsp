<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.sql.*" %>
<%@ page import="com.ParkingLot.model.User" %>
<% Class.forName("org.postgresql.Driver"); %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Parking Summary</title>
<STYLE type="text/css">
table.mainTable th{
	text-align: CENTER;
	background-color: #4CAF50;
    color: white;
}
input[type=submit] {
    width: 80%;
    
    margin-left:auto;
    margin-right:auto;
    background-color: #E91E63;
    color:  white;
    padding: 10px 10px;
    margin: 0px 0;
    border: none;
    border-radius: 2px;
    cursor: pointer;
}
table.mainTable th, td {
    padding: 15px;
    text-align: center;
}
table.mainTable{
    border-collapse: collapse;
    width:50%;
    margin-left:auto;
    margin-right:auto;
}

.heading{
	 background-color:#b71c1c;  
    color:white;  
    padding:4px;  
    text-align:center;  
}
table.buttonTable {
	
    width:50%;
    margin-left:auto;
    margin-right:auto;
    border: 0 px;
    
}
table.buttonTable td{
	border:none;

}
table.mainTable th, td {
    border: 1px solid black;
    border-collapse: collapse;
     
    margin-left:auto;
    margin-right:auto;
}
table.mainTable tr:nth-child(even) {background-color: #f2f2f2}
</STYLE>
</head>
<body>

<h2 class="heading">Parking Summary</h2>


  <% 
  			User user=new User();
            Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/test2", "postgres", "admin");

            Statement statement = connection.createStatement() ;
            String sql;
            %>
            <table class="mainTable" border=1>
                <TR>
                <TH>Floor Number</TH>
                <TH>Bike</TH>
                <TH>Car</TH>
                
                <TH>Bus</TH>
              
            </TR>
           <% for(int i=1;i<=user.getNumberOfFloors();i++)
            {
        	   %>
        	   <tr>
        	   	<td><%=i %></td>
        	   	<%
             sql="select count(*) from parkinglot where floornumber=? and vehicletype='Bike'";
        	   try(PreparedStatement pstmt=connection.prepareStatement(sql)){
               	pstmt.setInt(1, i);
               	ResultSet resultset = pstmt.executeQuery() ;
               	while(resultset.next())
               	{
               	%>
               	<td><%=resultset.getInt("count") %></td>
               	<%
               	}
        	   }
        	   catch(SQLException ex)
        	   {
        		   System.out.println(ex.getMessage());
        	   }
        	   
        	   
        	   sql="select count(*) from parkinglot where floornumber=? and vehicletype='Car'";
        	   try(PreparedStatement pstmt=connection.prepareStatement(sql)){
               	pstmt.setInt(1, i);
               	ResultSet resultset = pstmt.executeQuery() ;
               	while(resultset.next())
               	{
               	%>
               	<td><%=resultset.getInt("count") %></td>
               	<%
               	}
        	   }
        	   catch(SQLException ex)
        	   {
        		   System.out.println(ex.getMessage());
        	   }
        	   
        	   sql="select count(*) from parkinglot where floornumber=? and vehicletype='Bus'";
        	   try(PreparedStatement pstmt=connection.prepareStatement(sql)){
               	pstmt.setInt(1, i);
               	ResultSet resultset = pstmt.executeQuery() ;
               	while(resultset.next())
               	{
               	%>
               	<td><%=resultset.getInt("count") %></td>
               	<%
               	}
        	   }
        	   catch(SQLException ex)
        	   {
        		   System.out.println(ex.getMessage());
        	   }
        	   %>
        	   </tr>
        	   <%
               	
            }%>
            </table>
</body>
</html>