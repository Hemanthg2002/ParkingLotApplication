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
th{
	text-align: CENTER;
	background-color: #4CAF50;
    color: white;
}
input[type=submit] {
    width: 100%;
    background-color: #E91E63;
    color:  white;
    padding: 8px 8px;
    margin: 0px 0;
    border: none;
    border-radius: 2px;
    cursor: pointer;
}
th, td {
    padding: 15px;
    text-align: left;
}
table {
    border-collapse: collapse;
}

.heading{
	 background-color:#b71c1c;  
    color:white;  
    padding:4px;  
    text-align:center;  
}
table, th, td {
    border: 1px solid black;
}
tr:nth-child(even) {background-color: #f2f2f2}
</STYLE>
     </HEAD>

    <BODY>
        

        <% 
            Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/test2", "postgres", "admin");

            Statement statement = connection.createStatement() ;
            ResultSet resultset = 
                statement.executeQuery("select * from parkinglot") ; 
        %>
		<H1 class="heading" >Parking Lot Database Table </H1>
        <TABLE BORDER="1">
            <TR>
                <TH>Vehicle NO.</TH>
                <TH>Vehicle Type</TH>
                <TH>Floor Number</TH>
                <TH>Space Number</TH>
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
                <TD> <%= resultset.getString(6) %></TD>
            </TR>
            <% } %>
        </TABLE>
        
 <form action="fromEntryToDepart">
 	<input type="submit" value="To Depart">
 </form>
    </BODY>
</HTML>
