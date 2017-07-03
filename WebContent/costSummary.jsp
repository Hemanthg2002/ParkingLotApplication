<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
  <%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.sql.*" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Cost Summary of Different Vehicles</title>
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
      width:50%;
    margin-left:auto;
    margin-right:auto;
}
table.mainTable tr:nth-child(even) {background-color: #f2f2f2}
</STYLE>
</head>
<body>
 <%  Connection connection = DriverManager.getConnection( "jdbc:postgresql://localhost:5432/test2", "postgres", "admin");

            Statement statement = connection.createStatement() ;
            String sql="select * from costSummary";
         
           /* try(PreparedStatement pstmt=connection.prepareStatement(sql)){
            	pstmt.setInt(1, floorNumber);
            	ResultSet resultset = pstmt.executeQuery(sql) ; 
            }*/
           ResultSet resultset = statement.executeQuery(sql);
            
        %>
		<H2 class="heading" >Cost Summary Database Table </H2>
        <TABLE class="mainTable" BORDER="1">
            <TR>
              
                <TH>Vehicle Type</TH>
                <TH>Number Of Vehicles Processed</TH>
                
                <TH>Amount Collected</TH>
                
            </TR>
            <% while(resultset.next()){ %>
            <TR>
                <TD> <%= resultset.getString(1) %></td>
                <TD> <%= resultset.getInt(2) %></TD>
                <TD> <%= resultset.getInt(3) %></TD>
                
               
            </TR>
            <% } %>
        </TABLE>
        <!--  
<table class="buttonTable" cellspacing="0" cellpadding="0">
<tr>
	<td>
		<form action="fromDepartToEntry">
		 	<input type="submit" value="To Entry">
		 </form>
	</td>

	<td>
	 <form action="fromEntryToDepart">
 	<input type="submit" value="To Depart">
 </form>
	</td>
</tr>
 </table>
 -->
</body>
</html>