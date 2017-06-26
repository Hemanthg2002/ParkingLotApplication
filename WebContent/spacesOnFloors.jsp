<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Spaces On Floors</title>
<style>
body {
    background-color: #ffebee;
    align:center;
}
.formheading{  
    background-color:#b71c1c;  
    color:white;  
    padding:4px;  
    text-align:center;  
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
    padding: 12px 20px;
    margin: 8px 0;
    display: inline-block;
    border: 1px solid #ccc;
    border-radius: 4px;
    box-sizing: border-box;
}

input[type=submit] {
    width: 200px;
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

div {
    border-radius: 5px;
    background-color: #ffdce1;
    padding: 2px;
    width: 300 px;
    margin: 0 auto;
    align:center;
}
</style>
</head>
<body>
<h3 class="formheading"> 
The number of Floors is 
<s:property value="numberOfFloors"/>

<br>

The spaces on each Floors is:
<s:property value="spacesOnFloors"/>
</h3>
<div align="center">
<s:form action="vehicleEntry" align="center">
	<s:submit label="Vehicle Entry" value="Vehicle Entry">
	</s:submit>
</s:form>
<s:form action="vehicleDepart" align="center">
	<s:submit label="Vehicle Depart" value="Vehicle Depart">
	</s:submit>


</s:form>
</div>

</body>
</html>