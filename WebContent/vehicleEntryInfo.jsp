<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.sql.*" %>

<% Class.forName("org.postgresql.Driver"); %>

<HTML>
    <HEAD>
        <TITLE>Parking Lot Database Table </TITLE>      
        
        
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
     </HEAD>

    <BODY>
        

        <% 
            Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/test2", "postgres", "admin");

            Statement statement = connection.createStatement() ;
            String sql="select * from parkinglot";
         
           /* try(PreparedStatement pstmt=connection.prepareStatement(sql)){
            	pstmt.setInt(1, floorNumber);
            	ResultSet resultset = pstmt.executeQuery(sql) ; 
            }*/
           ResultSet resultset = statement.executeQuery(sql);
            
        %>
		<H1 class="heading" >Parking Lot Database Table </H1>
        <TABLE class="mainTable" BORDER="1">
            <TR>
                <TH>Vehicle NO.</TH>
                <TH>Vehicle Type</TH>
                <TH>Floor Number</TH>
                <Th>Space Number</Th>
                <TH>Entry Time</TH>
                <TH>Exit Time</TH>
            </TR>
            <% while(resultset.next()){ %>
            <TR>
                <TD> <%= resultset.getString(1) %></td>
                <TD> <%= resultset.getString(2) %></TD>
                <TD> <%= resultset.getString(3) %></TD>
                
                <TD> <%= resultset.getString(4) %></TD>
                <TD> <%= resultset.getString(5) %></TD>
                <Td> <%=resultset.getString(6) %></Td>
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
	<tr>
	<td>
	 <form action="parkingSummary">
 	<input type="submit" value="Parking Summary">
 </form>
	</td>
	<td>
	 <form action="costSummary">
 	<input type="submit" value="Cost Summary">
 </form>
	</td>
	
 </table>
 -->
 <table class="buttonTable" cellspacing="0" cellpadding="0">
 	<tr>
 		<td>
	 	<form action="mainTabs">
 			<input type="submit" value="Main Menu">
 		</form>
	</td>
 	</tr>
 </table>
    </BODY>
</HTML>
