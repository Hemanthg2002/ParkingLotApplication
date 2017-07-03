<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="refresh" content="30"/>
<title>Main with Tabs</title>
 <link rel = "stylesheet"
   type = "text/css"
   href = "mainWithTabsStyle.css" />
</head>
<body>


<!-- Vertical Tabs -->
<div class="tab">
  <button class="tablinks" onclick="openEvent(event, 'entryForm')" id="defaultOpen">ENTRY FORM</button>
  <button class="tablinks" onclick="openEvent(event, 'departForm')">DEPART FORM</button>
  <button class="tablinks" onclick="openEvent(event, 'parkingForm')">PARKING SUMMARY</button>
  <button class="tablinks" onclick="openEvent(event, 'costSummary')">COST SUMMARY</button>
</div>


<!-- Content Of The Tabs -->
<div id="entryForm" class="tabcontent">
  
  <h3 class="formheading">Vehicle Entry Form</h3>
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
</div>

<div id="departForm" class="tabcontent">

<h3 class="formheading">Vehicle Leaving Form</h3>  
<div class="firstDiv">
<div>
<s:form action="vehicleDepartAction" align="center">
	<s:textfield label="Vehicle Number :" key="vehicleNumber"/>
	<s:submit />
</s:form>
<s:property value="numberOfFloors"/>
</div>
</div>


</div>

<div id="parkingForm" class="tabcontent">
  <iframe frameborder=0 height=100% width=100% name="iframe3" src="http://localhost:8080/ParkingLotApplication/parkingSummary.jsp"></iframe>
</div>
<div id="costSummary" class="tabcontent">
  <iframe frameborder=0 height=100% width=100% name="iframe4" src="http://localhost:8080/ParkingLotApplication/costSummary.jsp"></iframe>
</div>

<!-- JavaScript Code -->
<script>
function openEvent(evt, cityName) {
    var i, tabcontent, tablinks;
    tabcontent = document.getElementsByClassName("tabcontent");
    for (i = 0; i < tabcontent.length; i++) {
        tabcontent[i].style.display = "none";
    }
    tablinks = document.getElementsByClassName("tablinks");
    for (i = 0; i < tablinks.length; i++) {
        tablinks[i].className = tablinks[i].className.replace(" active", "");
    }
    document.getElementById(cityName).style.display = "block";
    evt.currentTarget.className += " active";
}

// Get the element with id="defaultOpen" and click on it
document.getElementById("defaultOpen").click();
</script>

</body>
</html>