package com.ParkingLot.action;

import java.sql.Connection;
import java.sql.Statement;
import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import com.ParkingLot.model.User;
import com.opensymphony.xwork2.ActionSupport;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;


public class vehicleEntryAction extends ActionSupport{

	private String vehicleNumber;
	private int nextFloorNumber;
	private int nextSpaceNumber;
	private String selectedVehicleType=new String();
	private String info=new String();
	private String entryTime=new String();
	
	int vehicleId=1;
	User user =new User();
	
	//Validating Function.
	public void validate()
	{
		//Method for checking if Empty
		if(StringUtils.isEmpty(vehicleNumber))
		{
			addFieldError("vehicleNumber", "Vehicle Number cannot be Blank");
			info="Acccess Denied! Provide proper Vehicle Number.";
		}
		
			
		//Method for checking if entry is wrong.
				Connection c = null;
				Statement stmt=null;
				ResultSet rs=null;
				entryTime=null;
				int affectedrows=0;
				try {
					Class.forName("org.postgresql.Driver");
					c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/test2","postgres", "admin");
					c.setAutoCommit(false);
					
					//Creation of Statement.
					stmt=c.createStatement();
					
					
					String sql="select * from parkinglot where vehicle_no=?";
					affectedrows=0;
					
					try(PreparedStatement pstmt = c.prepareStatement(sql)){
							
							pstmt.setString(1,vehicleNumber);
							rs = pstmt.executeQuery();
							//affectedrows=pstmt.executeUpdate();
							System.out.println(affectedrows+" "+rs+"- from Validate.");
							while(rs.next())
							{
								//vehicleType=rs.getString("vehicletype");
								//floorNumber=rs.getInt("floornumber");
								entryTime=rs.getString("entrytime");
							}
						System.out.println(entryTime+"-from Validate");
						}
					catch(SQLException ex)
					{
						System.out.println(ex.getMessage()+" from validate Entry Action 1");
					}
					
					
					stmt.close();
					c.commit();
					c.close();
				} catch (Exception e) {
					e.printStackTrace();
					System.err.println(e.getClass().getName()+": "+e.getMessage());
					System.exit(0);
					}
				if(entryTime!=null)
				{
					info="Acccess Denied! Vehicle Number Already Present in the Parking Lot.";
					addFieldError("vehicleNumber", "Vehicle of given Vehicle Number is inside Parking Lot");
				}
				
			
	}
	
	
	//Main Execute Function
	public String execute() {
		//Opening Database Connection.
		Connection c = null;
		Statement stmt=null;
		
		if(selectedVehicleType.equals("Bike"))
				vehicleId=1;
		else if(selectedVehicleType.equals("Car"))
			vehicleId=2;
		else if(selectedVehicleType.equals("Bus"))
			vehicleId=3;
		try {
		Class.forName("org.postgresql.Driver");
		c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/test2","postgres", "admin");
		c.setAutoCommit(false);
		
		//Creation of Statement.
		stmt=c.createStatement();
		
		//Dynamic Update Of Next Free Space
		updateNextFreeSpace(selectedVehicleType);

		//getting values from database.
		ResultSet rs=null;
		String sql3="select * from nextfreespace where free_space_id=?";
		try(PreparedStatement pstmt=c.prepareStatement(sql3)){
			pstmt.setInt(1, vehicleId);;
			rs=pstmt.executeQuery();
			while(rs.next())
			{
				nextFloorNumber=rs.getInt("floornumber");
				nextSpaceNumber=rs.getInt("spacenumber");
				System.out.println(nextFloorNumber+" "+nextSpaceNumber+" inside getting values from nextfreespace");
			}
		}
		catch(SQLException ex)
		{
			System.out.println(ex.getMessage());
		}
		
		
		//HttpSession actionRequest = null ;
		//HttpSession session = actionRequest.getSession();
		//actionRequest .setAttribute("floorNumber", nextFloorNumber);
		
		
		String sql="insert into parkinglot(vehicle_no,vehicletype,floornumber,spacenumber,entrytime)"+" values(?,?,?,?,current_timestamp)";
		int affectedrows=0;
		
		try(PreparedStatement pstmt = c.prepareStatement(sql)){
			
			pstmt.setString(1,vehicleNumber);
			pstmt.setString(2, this.selectedVehicleType);
			pstmt.setInt(3,nextFloorNumber);
			String spaceNumberText=new String();
			spaceNumberText=selectedVehicleType+"-"+Integer.toString(nextSpaceNumber);
			pstmt.setString(4,spaceNumberText);
			System.out.println(sql);
			affectedrows = pstmt.executeUpdate();
			System.out.println(affectedrows);
			System.out.println(pstmt+"entry pstmt");
		}
		catch(SQLException ex)
		{
			System.out.println(ex.getMessage());
		}
		
		//update vehiclecpaces Table.
		updateVehicleSpacesTable();
		
		
		//Updating vehicle Limit table
		updateVehicleLimitTable();
				
		
		//updating the occupied flag in occupied flag table
		sql="update occupiedflag set occupied=1 where floornumber=? and spacenumber=?";
		try(PreparedStatement pstmt1=c.prepareStatement(sql))
				{
					pstmt1.setInt(1, nextFloorNumber);
					pstmt1.setInt(2, nextSpaceNumber);
					affectedrows=pstmt1.executeUpdate();
					
				}
		catch(SQLException ex)
		{
			System.out.println(ex.getMessage());
		}
		
		
		
		
	
		
		//sql="update nextfreespace set spacenumber=spacenumber+1 where free_space_id=1";
		
		//stmt.executeUpdate(sql);
		
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

	private void updateVehicleLimitTable() {
		// TODO Auto-generated method stub
		
		Connection c = null;
		Statement stmt=null;
		try {
			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/test2","postgres", "admin");
			c.setAutoCommit(false);
			
			//Creation of Statement.
			stmt=c.createStatement();
		
			String sqlbike="update vehiclelimit set bike=bike+1 where floornumber=?";
			String sqlcar="update vehiclelimit set car=car+1 where floornumber=?";
			String sqlbus="update vehiclelimit set bus=bus+1 where floornumber=?";
			String sql4=new String();
			if(vehicleId==1)
				sql4=sqlbike;
			else if(vehicleId==2)
				sql4=sqlcar;
			else if(vehicleId==3)
				sql4=sqlbus;
			
			int affectedrows=0;
			try(PreparedStatement pstmt=c.prepareStatement(sql4)){
				pstmt.setInt(1, nextFloorNumber);
				System.out.println(pstmt+" Pstatement vehicle limit updation");
				affectedrows=pstmt.executeUpdate();
				System.out.println(affectedrows);
			}
			catch(SQLException ex)
			{
				System.out.println(ex.getMessage());
			}
			
			stmt.close();
			c.commit();
			c.close();
			} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName()+": "+e.getMessage());
			System.exit(0);
			}
		
	}


	private void updateVehicleSpacesTable() {
		// TODO Auto-generated method stub
		
		Connection c = null;
		Statement stmt=null;
		try {
		Class.forName("org.postgresql.Driver");
		c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/test2","postgres", "admin");
		c.setAutoCommit(false);
		
		//Creation of Statement.
		stmt=c.createStatement();
		
		String sql=new String();
		if(selectedVehicleType.equals("Bike"))
			sql="update bikespaces set flag=1 where floornumber=? and spacenumber=?";
		else if(selectedVehicleType.equals("Car"))
			sql="update carspaces set flag=1 where floornumber=? and spacenumber=?";
		else if(selectedVehicleType.equals("Bus"))
			sql="update busspaces set flag=1 where floornumber=? and spacenumber=?";
		
		int affectedrows=0;
		
		try(PreparedStatement pstmt1=c.prepareStatement(sql))
		{
			pstmt1.setInt(1, nextFloorNumber);
			pstmt1.setInt(2, nextSpaceNumber);
			affectedrows=pstmt1.executeUpdate();
			
		}
		catch(SQLException ex)
		{
			System.out.println(ex.getMessage());
		}

		
		
		
		stmt.close();
		c.commit();
		c.close();
		} catch (Exception e) {
		e.printStackTrace();
		System.err.println(e.getClass().getName()+": "+e.getMessage());
		System.exit(0);
		}

	}


	public String getVehicleNumber() {
		return vehicleNumber;
	}

	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}

	public String getSelectedVehicleType() {
		return selectedVehicleType;
	}
	public void updateNextFreeSpace(String selectedVehicleType)
	{
		
		//database connections.
		
		Connection c = null;
		Statement stmt=null;
		try {
		Class.forName("org.postgresql.Driver");
		c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/test2","postgres", "admin");
		c.setAutoCommit(false);
		
		//Creation of Statement.
		stmt=c.createStatement();
		 
		 //Getting Floor number and space number from user Bean.
		 int numberOfFloors=user.getNumberOfFloors();
		 int numberOfSpaces=user.getSpacesOnFloors();
		 int vehicleLimit=0;
		 //Initialising Vehicle limit on Floors.
		 int carLimit=(numberOfSpaces*2)/13;
		 int busLimit=(numberOfSpaces*2)/13;
		 int bikeLimit=numberOfSpaces-(carLimit*2)-(busLimit*4);
		 //Setting Vehicle Limit and Vehicletype;
		 if(vehicleId==1)
			 vehicleLimit=bikeLimit;
		 else if(vehicleId==2)
			 vehicleLimit=carLimit;
		 else if(vehicleId==3)
			 vehicleLimit=busLimit;
		 
		 
		 
		 //nextSpaceNumber=nextSpaceNumber+1;
		 int flag=0,affectedrows;
		 for(int i=1;i<=numberOfFloors;i++)
		 {
			 int countLimit=0;
			 String sql2="select * from vehiclelimit where floornumber=?";
			 try(PreparedStatement pstmt = c.prepareStatement(sql2)){
				 
				 pstmt.setInt(1, i);
				 ResultSet rs2=pstmt.executeQuery();
					
					while(rs2.next())
					{
						countLimit=rs2.getInt(selectedVehicleType);
					}
				 
			 	}
				catch(SQLException ex)
				{
					System.out.println(ex.getMessage());
				}
			 
			 if(countLimit<vehicleLimit)
			 {
				  String sql1="update nextfreespace set floornumber=?  where free_space_id=?";
					try(PreparedStatement pstmt1 = c.prepareStatement(sql1)){
						
						pstmt1.setInt(1,i);
						pstmt1.setInt(2, vehicleId);
						affectedrows = pstmt1.executeUpdate();
						System.out.println(affectedrows);
					}
					catch(SQLException ex)
					{
						System.out.println(ex.getMessage());
					}
					
					
				 for(int j=1;j<=vehicleLimit;j++)
				 {
					 System.out.println(i+" "+j+ " Calculating Space Number");
					 String sql=new String();
					 if(selectedVehicleType.equals("Bike"))
							sql="select * from bikespaces where floornumber=? and spacenumber=?";
					 else if(selectedVehicleType.equals("Car"))
					 		sql="select * from carspaces where floornumber=? and spacenumber=?";
					 else if(selectedVehicleType.equals("Bus"))
						 	sql="select * from busspaces where floornumber=? and spacenumber=?";
					 
					 try(PreparedStatement pstmt = c.prepareStatement(sql)){
							
							pstmt.setInt(1,i);
							pstmt.setInt(2,j);
							
							ResultSet rs=pstmt.executeQuery();
							
							while(rs.next())
							{
								int occupiedFlag=rs.getInt("flag");
								if(occupiedFlag==1)
								{
									continue;
								}
								else
								{
									System.out.println(i+" "+j+"inside space Calculation");
									sql1="update nextfreespace set spacenumber=?  where free_space_id=? ";
									try(PreparedStatement pstmt1 = c.prepareStatement(sql1)){
										
										pstmt1.setInt(1,j);
										pstmt1.setInt(2, vehicleId);
										
										affectedrows = pstmt1.executeUpdate();
										System.out.println(affectedrows);
									}
									catch(SQLException ex)
									{
										System.out.println(ex.getMessage());
									}
									
									/*
									 sql1="update nextfreespace set floornumber=?  where free_space_id=?";
									try(PreparedStatement pstmt1 = c.prepareStatement(sql1)){
										
										pstmt1.setInt(1,i);
										pstmt1.setInt(2, vehicleId);
										affectedrows = pstmt1.executeUpdate();
										System.out.println(affectedrows);
									}
									catch(SQLException ex)
									{
										System.out.println(ex.getMessage());
									}
									*/
											
											
											
											
											flag=1;
											break;
								}
								
							}
							
						}
						catch(SQLException ex)
						{
							System.out.println(ex.getMessage());
						}
					 	if(flag==1)
						{
							break;
						}
					
				 }
					//break;
			 }
			 if(flag==1)
				{
					break;
				}
			
		 }
			 	
				stmt.close();
				c.commit();
				c.close();
				} catch (Exception e) {
				e.printStackTrace();
				System.err.println(e.getClass().getName()+": "+e.getMessage());
				System.exit(0);
				}
		
	}

	public void setSelectedVehicleType(String selectedVehicleType) {
		//this.selectedVehicleType = selectedVehicleType;
		if(selectedVehicleType.equals("1"))
		{
			this.selectedVehicleType="Bike";
		}
		else if(selectedVehicleType.equals("2"))
		{
			this.selectedVehicleType="Car";
		}
		else if(selectedVehicleType.equals("3"))
		{
			this.selectedVehicleType="Bus";
		}
		System.out.println(this.selectedVehicleType+"selectedVehicleType");
	}
	
	
}
