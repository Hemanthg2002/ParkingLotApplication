package com.ParkingLot.action;
import java.util.regex.*;

import org.apache.commons.lang.StringUtils;

import com.ParkingLot.model.User;
import com.opensymphony.xwork2.ActionSupport;  
import java.sql.Connection;
import java.sql.DriverManager;

public class indexAction extends ActionSupport {
	
	//Parameters for Storing Values.
	private String numberOfFloors;
	private int integerNumberOfFloors;
	private String info=new String();
	private String spacesOnFloors;
	User user = new User();
	
	//Validate function to validate the number of Floors.
	public void validate()
	{
		//Method for checking if Empty
		if(StringUtils.isEmpty(numberOfFloors))
		{
			addFieldError("numberOfFloors", "Number Of Floors cannot be Blank");
			info="Acccess Denied! Provide proper number of Floors.";
		}
		
		//Regex method to match pattern.
		if(Pattern.matches("[0-9]{1,}", numberOfFloors))
			return;
		else
			{
				info="Acccess Denied! Provide proper number of Floors.";
				addFieldError("numberOfFloors", "Number Of Floors sould be a valid number.");
			}
	}
	
	
	//Getters and Setters for numberOfFloors
	
	public int getNumberOfFloors() {
		if(Pattern.matches("[0-9]{1,}", numberOfFloors))
			return Integer.parseInt(numberOfFloors);
		else
			{
				info="Acccess Denied! Provide proper number of Floors.";
				return 0;	
			}
	}
	public void setNumberOfFloors(String numberOfFloors) {
		this.numberOfFloors = numberOfFloors;
		int integerNumberOfFloors=Integer.parseInt(numberOfFloors);
        user.setNumberOfFloors(integerNumberOfFloors);
		
	}

	
	//Main action method
	public String execute()
	{   
		Connection c = null;
		try {
		Class.forName("org.postgresql.Driver");
		c = DriverManager
		.getConnection("jdbc:postgresql://localhost:5432/test2",
		"postgres", "admin");
		} catch (Exception e) {
		e.printStackTrace();
		System.err.println(e.getClass().getName()+": "+e.getMessage());
		System.exit(0);
		}
		System.out.println("Opened database successfully");
		
		System.out.println(integerNumberOfFloors);
		if(numberOfFloors.equals("")||numberOfFloors.equals("null")||Integer.parseInt(numberOfFloors)==0)
			return "error";
		else
		{
			if(Pattern.matches("[0-9]{1,}", numberOfFloors))
				
				return "success";
			else
				{
					info="Acccess Denied! Provide proper number of Floors.";
					return "error";
					
				}
			
		}
		
	}
	
	//Getters and Setters for Integer number of Floors
	public int getIntegerNumberOfFloors() {
		return integerNumberOfFloors;
	}
	
	public void setIntegerNumberOfFloors() {
		this.integerNumberOfFloors = getNumberOfFloors();
	}
	
	//Getters and Setters for Info String
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	
	//Getters and setters for spaces on floors.
	public int getSpacesOnFloors() {
		if(Pattern.matches("[0-9]{1,}",spacesOnFloors))
			return Integer.parseInt(spacesOnFloors);
		else
			{
				info="Acccess Denied! Provide proper number of Floors.";
				return 0;
				
			}
	}
	public void setSpacesOnFloors(String spacesOnFloors) {
		this.spacesOnFloors = spacesOnFloors;
		
		user.setSpacesOnFloors(Integer.parseInt(spacesOnFloors));
	}
		
}
