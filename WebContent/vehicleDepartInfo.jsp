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
table.mainTable th{
	text-align: CENTER;
	background-color: #4CAF50;
    color: white;
}
table.mainTable th, td {
    padding: 15px;
    
}
table.mainTable {
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
    text-align:center;
}
table.mainTable tr:nth-child(even) {background-color: #f2f2f2}

input[type=submit] {
    width: 100%;
    margin-left:auto;
    margin-right:auto;
    background-color: #E91E63;
    color:  white;
    padding: 10px 10px;
    
    border: none;
    border-radius: 2px;
    cursor: pointer;
}
</STYLE>
<body>
<h3 class="heading"> Vehicle Depart Info</h3>
<TABLE class="mainTable" BORDER="1">
     <Tr>
         <TH>Vehicle NO.</TH>
         <TD> <s:property value="vehicleNumber"/></td>
     </Tr>
     
     <TR>
         <TH>Vehicle Type</TH>
      	<TD> <s:property value="vehicleType"/></TD>
     </TR>
     
      <TR>
         <TH>Floor Number</TH>
         <TD> <s:property value="floorNumber"/></TD>
      </TR>
      
      <TR>
         
         <TH>Entry Time</TH>
         <TD> <s:property value="entryTime"/></TD>
      </TR>
       
       <TR>
         <TH>Exit Time</TH>
         <TD> <s:property value="exitTime"/></TD>
       </TR>
       
       <TR>
       		<TH>Parking Duration</TH>
       		<TD><s:property value="duration"/> hrs.</TD>
       </TR>
       <tr>
       <Th>Cost Calculation</Th>
       <td>
       <s:property value="costInformation"/>
       
       </td>
       </tr>
       
       <TR>
       		<TH>Total Parking Cost</TH>
       		<Td><s:property value="payment"/></Td>
       		</TR>

		
	    
		
		
		
           

</table>
<!--  
<table class="buttonTable">
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
	
</tr>
 </table>
 
 -->
 <table class="buttonTable">
 	<tr>
 		<td>
 			<form action="mainTabs">
 			<input type="submit" value="Main Menu">
			 </form>
 		</td>
 	</tr>
 </table>
</body>
</html>