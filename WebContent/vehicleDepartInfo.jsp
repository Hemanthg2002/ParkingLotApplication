<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Vehicle Depart Info</title>
</head>
<STYLE type="text/css">
th{
	text-align: CENTER;
	background-color: #4CAF50;
    color: white;
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
</STYLE>
<body>
<h3 class="heading"> Vehicle Depart Info</h3>
<TABLE BORDER="1">
     <TR>
         <TH>Vehicle NO.</TH>
         <TH>Vehicle Type</TH>
         <TH>Floor Number</TH>
         <TH>Space Number</TH>
         <TH>Entry Time</TH>
         <TH>Exit Time</TH>
	</TR>
	<TR>
		<TD> <s:property value="vehicleNumber"/></td>
		<TD> <s:property value="vehicleType"/></TD>
	    <TD> <s:property value="floorNumber"/></TD>
		<TD> <s:property value="spaceNumber"/></TD>
		<TD> <s:property value="entryTime"/></TD>
		<TD> <s:property value="exitTime"/></TD>
            </TR>

</table>
<form action="fromDepartToEntry">
 	<input type="submit" value="To Entry">
 </form>
</body>
</html>