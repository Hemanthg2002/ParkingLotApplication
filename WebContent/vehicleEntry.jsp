<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Vehicle Entry</title>
<style>
body {
    background-color: #ffebee;
    align:center;
}

h1 {
    color: white;
    text-align: center;
}

p {
    font-family: roboto;
    font-size: 20px;
    align: center;
    font-color:white
}
input[type=text], select {
    width: 100%;
    padding: 12px 0px;
    margin: 8px 0;
    display: inline-block;
    border: 1px solid #ccc;
    border-radius: 4px;
    box-sizing: border-box;
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

input[type=submit]:hover {
    background-color: ##E91E69;
  
}

.firstDiv {
    border-radius: 5px;
    background-color: #ffdce1;
    padding: 2px;
    align:center;
    width: 500px;
    margin: 0 auto;
}
div
{
margin:0 auto;
align:center;
padding:10px;
}
.formheading{  
    background-color:#b71c1c;  
    color:white;  
    padding:4px;  
    text-align:center;  
}
.vertical-menu {
    width: 200px; /* Set a width if you like */
}

.vertical-menu a {
    background-color: #eee; /* Grey background color */
    color: black; /* Black text color */
    display: block; /* Make the links appear below each other */
    padding: 12px; /* Add some padding */
    text-decoration: none; /* Remove underline from links */
}

.vertical-menu a:hover {
    background-color: #ccc; /* Dark grey background on mouse-over */
}

.vertical-menu a.active {
    background-color: #4CAF50; /* Add a green color to the "active/current" link */
    color: white;
}
</style>
</head>
<body>
 


<h3 class="formheading">Vehicle entry Form</h3>
<div class="firstDiv">
<div>
<s:form action="vehicleEntryAction" align="center">
	<s:textfield label="Vehicle Number :" key="vehicleNumber"/>
	<s:select label="Vehicle Type" name="selectedVehicleType" list="#{'1':'Bike','2':'Car','3':'Bus'}" key="selectedVehicleType" required="true">
	
	</s:select>
	
	
	<s:submit />
</s:form>
</div>
</div>
</body>
</html>