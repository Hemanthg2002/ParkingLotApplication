package com.ParkingLot.action;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class vehicleDepartAction {
	
	private String vehicleNumber=new String();
	private String vehicleType=new String();
	private int floorNumber;
	private int spaceNumber;	
	private String entryTime;
	private String exittime;
	

	public String execute(){
	//Opening Database connections.
	
		Connection c = null;
		Statement stmt=null;
		try {
			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/test2","postgres", "admin");
			c.setAutoCommit(false);
			
			//Creation of Statement.
			stmt=c.createStatement();
			
			//getting values from database.
			String sql="select * from parkinglot where vehicle_no=?";
			int affectedrows=0;
			
			try(PreparedStatement pstmt = c.prepareStatement(sql)){
				
				pstmt.setString(1,vehicleNumber);
				ResultSet rs = pstmt.executeQuery();
				while(rs.next())
				{
					vehicleType=rs.getString("vehicletype");
					floorNumber=rs.getInt("floornumber");
					spaceNumber=rs.getInt("spacenumber");
					entryTime=rs.getString("entrytime");
				}
				System.out.println(vehicleType+" "+floorNumber);
			}
			sql="insert into parkinglotarchive(vehicle_no,vehicletype,floornumber,spacenumber,entrytime,exittime)"+" values(?,?,?,?,?,current_timestamp)";
			try(PreparedStatement pstmt = c.prepareStatement(sql)){
				pstmt.setString(1,vehicleNumber);
				pstmt.setString(2,vehicleType);
				pstmt.setInt(3,floorNumber);
				pstmt.setInt(4,spaceNumber);
				pstmt.setString(5,entryTime);
				
				affectedrows=pstmt.executeUpdate();
				System.out.println(pstmt);
				System.out.println(affectedrows);
			}
			catch(SQLException ex)
			{
				System.out.println(ex.getMessage());
			}
			sql="select * from parkinglotarchive where vehicle_no=?";
			affectedrows=0;
			
			try(PreparedStatement pstmt = c.prepareStatement(sql)){
				
				pstmt.setString(1,vehicleNumber);
				ResultSet rs = pstmt.executeQuery();
				while(rs.next())
				{
					exitTime=rs.getString("exittime");
				}
				System.out.println(vehicleType+" "+floorNumber);
			}
			//closing all.
		
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
	public String getVehicleType() {
		return vehicleType;
	}
	private String exitTime;
	public int getFloorNumber() {
		return floorNumber;
	}
	public int getSpaceNumber() {
		return spaceNumber;
	}
	public String getEntryTime() {
		return entryTime;
	}
	public String getExitTime() {
		return exitTime;
	}
}
