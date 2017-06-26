package com.ParkingLot.action;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;


public class vehicleEntryAction {

	private String vehicleNumber;
	private int nextFloorNumber;
	private int nextSpaceNumber;
	public String execute() {
		//Opening Database Connection.
		Connection c = null;
		Statement stmt=null;
		try {
		Class.forName("org.postgresql.Driver");
		c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/test2","postgres", "admin");
		c.setAutoCommit(false);
		
		//Creation of Statement.
		stmt=c.createStatement();
		

		//getting values from database.
		ResultSet rs=stmt.executeQuery("select * from nextfreespace");
		while(rs.next())
		{
			nextFloorNumber=rs.getInt("floornumber");
			nextSpaceNumber=rs.getInt("spacenumber");
			System.out.println(nextFloorNumber+nextSpaceNumber);
		}
		String sql="insert into parkinglot(vehicle_no,vehicletype,floornumber,spacenumber,entrytime)"+" values(?,'car',?,?,current_timestamp)";
		int affectedrows=0;
		
		try(PreparedStatement pstmt = c.prepareStatement(sql)){
			
			pstmt.setString(1,vehicleNumber);
			pstmt.setInt(2,nextFloorNumber);
			pstmt.setInt(3,nextSpaceNumber);
			System.out.println(sql);
			affectedrows = pstmt.executeUpdate();
			System.out.println(affectedrows);
		}
		catch(SQLException ex)
		{
			System.out.println(ex.getMessage());
		}
		sql="update nextfreespace set spacenumber=spacenumber+1 where free_space_id=1";
		
		stmt.executeUpdate(sql);
		
		//closing all.
		rs.close();
		stmt.close();
		c.commit();
		c.close();
		} catch (Exception e) {
		e.printStackTrace();
		System.err.println(e.getClass().getName()+": "+e.getMessage());
		System.exit(0);
		}
		System.out.println("Opened database successfully");
		
		return "success";
	}

	public String getVehicleNumber() {
		return vehicleNumber;
	}

	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}
	
	
}
