<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Parking Lot Application</title>
<style>
body{


}


.formHeading{  
    background-color:#4CAF78;  
    color:white;  
    padding:4px;  
    text-align:center;
    font-family: Lucida Grande;  
}
input[type=text] {
    border: none;
    border-bottom: 2px solid green;
    
    padding: 12px 20px;
    margin: 8px 0;
    box-sizing: border-box;
}
input[type=submit]
{
 	background-color: #4CAF50;
 	width:250px;
    border: none;
    color: white;
    padding: 16px 32px;
    text-decoration: none;
    margin: 4px 2px;
    cursor: pointer;
}

div {
    
    background-color: #f2f2f2;
    padding:20px;
    
    margin:0px auto;
  
    width:700px;
}
</style>
</head>
<body>
<div>

	<h3 class="formHeading">PARKING LOT APPLICATION</h3>
	<s:form action ="indexAction" >
	
		<s:textfield label="Number Of Floors:" key="numberOfFloors"/><br>
		
		<s:textfield type="text" label="Spaces on each Floors:" key="spacesOnFloors"/><br>
		<s:submit value="Submit" align="center" padding="0px"/>
	</s:form>



</div>


</body>
</html>