package com.ParkingLot.action;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.*;

import org.apache.commons.lang.StringUtils;

import com.ParkingLot.model.User;
import com.opensymphony.xwork2.ActionSupport;  
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class indexAction extends ActionSupport {
	
	//Parameters for Storing Values.
	private String numberOfFloors;
	private int integerNumberOfFloors;
	private String info=new String();
	private String spacesOnFloors;
	private List<String> vehicleTypes;
	private String selectedVehicleType;
	
	User user = new User();
	//Initialising vehicle Type.
	
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
	
	


	
	//Main action method
	public String execute()
	{   
		//Initialising Vehicle Types List.

		vehicleTypes=new ArrayList<String>();
		vehicleTypes.add("Car");
		vehicleTypes.add("Bike");
		vehicleTypes.add("Bus");
		
		//data base Connections
		Connection c = null;
		try {
		Class.forName("org.postgresql.Driver");
		c = DriverManager
		.getConnection("jdbc:postgresql://localhost:5432/test2",
		"postgres", "admin");
		c.setAutoCommit(false);
		
		Statement stmt = c.createStatement();
		
		//dropping ParkingLot
		String sql="drop table parkinglot";
		stmt.executeUpdate(sql);
		//Creating Parking Lot
		sql="create table parkinglot( vehicle_no text not null PRIMARY KEY,vehicletype text not null,floornumber integer not null,spacenumber text,entrytime text not null,exittime text)";
		stmt.executeUpdate(sql);
		
		/*
		//Dropping occupiedFlag
		sql="drop table occupiedFlag";
		stmt.executeUpdate(sql);
		//Creating occupiedFlag.
		sql="create table occupiedFlag(floorNumber int,spaceNumber int,occupied int)";
		stmt.executeUpdate(sql);
		*/
		
		//Dropping costsummary
		sql="drop table costsummary";
		stmt.executeUpdate(sql);
		//Creating occupiedFlag.
		sql="create table costsummary(vehicletype text,numberofvehicles int,amountcollected int)";
		stmt.executeUpdate(sql);
		
		//Dropping vehiclelimit
		sql="drop table vehiclelimit";
		stmt.executeUpdate(sql);
		//Creating vehicle limit table.
		sql="create table vehiclelimit(floorNumber int PRIMARY KEY,bike int,car int,bus int)";
		stmt.executeUpdate(sql);
		
		//drop car spaces table.
		sql="drop table carspaces";
		stmt.executeUpdate(sql);
		//Creating carspaces table.
		sql="create table carspaces(floornumber int,spacenumber int,flag int)";
		stmt.executeUpdate(sql);
		
		
		//drop bike spaces table.
		sql="drop table bikespaces";
		stmt.executeUpdate(sql);
		//Creating bikespaces table.
		sql="create table bikespaces(floornumber int,spacenumber int,flag int)";
		stmt.executeUpdate(sql);
		
		//drop Bus Spaces table.
		sql="drop table busspaces";
		stmt.executeUpdate(sql);
		//creating bus spaces table.
		sql="create table busspaces(floornumber int,spacenumber int,flag int)";
		stmt.executeUpdate(sql);
		
		
		//initialising CostSummary Table
		
		
		//initCostSummaryTable();
		
		
		sql="insert into costsummary values('Bike',0,0)";
		stmt.executeUpdate(sql);
		sql="insert into costsummary values('Car',0,0)";
		stmt.executeUpdate(sql);
		sql="insert into costsummary values('Bus',0,0)";
		stmt.executeUpdate(sql);
		
		
		int affectedrows=0;
		int intFloors=Integer.parseInt(numberOfFloors);
		int intSpaces=Integer.parseInt(spacesOnFloors);
		
		
		//Initializing the carspaces table
		sql="insert into carspaces(floorNumber,spaceNumber,flag)"+" values(?,?,0)";
		int carLimit=(intSpaces*2)/13;
		for(int i=1;i<=intFloors;i++)
		{
			for(int j=1;j<=carLimit;j++)
			{
				try(PreparedStatement pstmt = c.prepareStatement(sql)){
					
					pstmt.setInt(1,i);
					pstmt.setInt(2,j);
					affectedrows = pstmt.executeUpdate();
					System.out.println(affectedrows);
				}
				catch(SQLException ex)
				{
					System.out.println(ex.getMessage());
				}
			}
		}
		
		//Initialising Bus Spaces Table.
		
				sql="insert into busspaces(floorNumber,spaceNumber,flag)"+" values(?,?,0)";
				int busLimit=(intSpaces*2)/13;
				for(int i=1;i<=intFloors;i++)
				{
					for(int j=1;j<=busLimit;j++)
					{
						try(PreparedStatement pstmt = c.prepareStatement(sql)){
							
							pstmt.setInt(1,i);
							pstmt.setInt(2,j);
							affectedrows = pstmt.executeUpdate();
							System.out.println(affectedrows);
						}
						catch(SQLException ex)
						{
							System.out.println(ex.getMessage());
						}
					}
				}
		
				
			//Initialising BikeSpaces Table.
				
				sql="insert into bikespaces(floorNumber,spaceNumber,flag)"+" values(?,?,0)";
				int bikeLimit=intSpaces-(carLimit*2)-(busLimit*4);
				for(int i=1;i<=intFloors;i++)
				{
					for(int j=1;j<=bikeLimit;j++)
					{
						try(PreparedStatement pstmt = c.prepareStatement(sql)){
							
							pstmt.setInt(1,i);
							pstmt.setInt(2,j);
							affectedrows = pstmt.executeUpdate();
							System.out.println(affectedrows);
						}
						catch(SQLException ex)
						{
							System.out.println(ex.getMessage());
						}
					}
				}
				
		//intitalising the vehiclelimit table.
		
		sql="insert into vehicleLimit(floorNumber,bike,car,bus)"+" values(?,0,0,0)";
		affectedrows=0;
		intFloors=Integer.parseInt(numberOfFloors);
		//intSpaces=Integer.parseInt(spacesOnFloors);
				for(int i=1;i<=intFloors;i++)
				{
					
						try(PreparedStatement pstmt = c.prepareStatement(sql)){
							
							pstmt.setInt(1,i);
							
							affectedrows = pstmt.executeUpdate();
							System.out.println(affectedrows);
						}
						catch(SQLException ex)
						{
							System.out.println(ex.getMessage());
						}
					
				}
		/*
		//Initializing the nextfreespace table.
		sql="update nextfreespace set floornumber=1 where free_space_id=1";
		stmt.executeUpdate(sql);
		sql="update nextfreespace set spacenumber=1 where free_space_id=1";
		stmt.executeUpdate(sql);
		sql="update nextfreespace set floornumber=1 where free_space_id=2";
		stmt.executeUpdate(sql);
		sql="update nextfreespace set spacenumber=1 where free_space_id=2";
		stmt.executeUpdate(sql);
		sql="update nextfreespace set floornumber=1 where free_space_id=3";
		stmt.executeUpdate(sql);
		sql="update nextfreespace set spacenumber=1 where free_space_id=3";
		stmt.executeUpdate(sql);
		*/
		
		stmt.close();
		c.commit();
		c.close();
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
	
	private void initCostSummaryTable() {
		// TODO Auto-generated method stub
		Connection c = null;
		Statement stmt=null;
		try {
			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/test2","postgres", "admin");
			c.setAutoCommit(false);
			
			stmt = c.createStatement();
			String sql="insert into costsummary values('Bike',0,0)";
			stmt.executeUpdate(sql);
			sql="insert into costsummary values('Car',0,0)";
			stmt.executeUpdate(sql);
			sql="insert into costsummary values('Bus',0,0)";
			stmt.executeUpdate(sql);
			
			stmt.close();
			c.commit();
			c.close();
		}
		catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName()+": "+e.getMessage());
			System.exit(0);
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
	//Getters and Setters for Vehicle Types.
	public List<String> getVehicleTypes() {
		return vehicleTypes;
	}


	public void setVehicleTypes(List<String> vehicleTypes) {
		this.vehicleTypes = vehicleTypes;
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


	public String getSelectedVehicleType() {
		return selectedVehicleType;
	}


	public void setSelectedVehicleType(String selectedVehicleType) {
		this.selectedVehicleType = selectedVehicleType;
	}
		
}
