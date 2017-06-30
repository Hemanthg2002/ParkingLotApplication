package com.ParkingLot.action;

import java.sql.Connection;
import java.sql.Statement;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.ParkingLot.model.User;
import com.opensymphony.xwork2.ActionSupport;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class vehicleDepartAction extends ActionSupport {
	
	private String vehicleNumber=new String();
	private String vehicleType=new String();
	private int floorNumber;
	private String spaceNumber;	
	private String entryTime;
	private String exittime;
	private int duration;
	private int payment;
	private String info;
	private String costInformation=new String();
	int result;
	
	User user =new User();
	
	public void validate()
	{
		//Method for checking if entry is wrong.
		Connection c = null;
		Statement stmt=null;
		try {
			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/test2","postgres", "admin");
			c.setAutoCommit(false);
			
			//Creation of Statement.
			stmt=c.createStatement();
			
			String sql="select * from parkinglot where vehicle_no=?";
			int affectedrows=0;
			
			try(PreparedStatement pstmt = c.prepareStatement(sql)){
					
					pstmt.setString(1,vehicleNumber);
					ResultSet rs = pstmt.executeQuery();
					while(rs.next())
					{
						vehicleType=rs.getString("vehicletype");
						floorNumber=rs.getInt("floornumber");
						entryTime=rs.getString("entrytime");
					}
				System.out.println(vehicleType+" "+floorNumber);
				}
			catch(SQLException ex)
			{
				System.out.println(ex.getMessage()+" from getvalues1");
			}
			
			
			stmt.close();
			c.commit();
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName()+": "+e.getMessage());
			System.exit(0);
			}
		if(entryTime==null)
		{
			info="Acccess Denied! Provide proper vehicle Number.";
			addFieldError("vehicleNumber", "Vehicle of given Vehicle Number is not inside Parking Lot");
		}
		
	}
	
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
			result=getValuesFromDatabase();
			if (result==1)
				return "error";
			
			//Updating vehicle Limit table.
			updateVehicleLimitTable();
			
			//updating Vehicle Spaces Table;
			updateVehicleSpacesTable();
		
			//Moving Data from current Database to Archived Database
			moveIntoArchivedDatabase();
			
			//Update Exit Time
			updateExitTime();
			
			
			
			//deleting this vehicle entry from active parking lot table.
			deleteEntryFromActiveTable();
			
			
			//Calculating duration for payment transaction
			duration=calculateDuration(entryTime, exitTime);
			
			//Calculating Payment Info.
			payment=calculatePayment(duration,vehicleType);
			
			
			//updating Cost Summary Table.
			updateCostSummaryTable();
			
			//closing all
			stmt.close();
			c.commit();
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName()+": "+e.getMessage());
			System.exit(0);
			}
		
		
		return "success";
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
			if(vehicleType.equals("Bike"))
				sql="update bikespaces set flag=0 where floornumber=? and spacenumber=?";
			else if(vehicleType.equals("Car"))
				sql="update carspaces set flag=0 where floornumber=? and spacenumber=?";
			else if(vehicleType.equals("Bus"))
				sql="update busspaces set flag=0 where floornumber=? and spacenumber=?";
			
			int affectedrows=0;
			
			try(PreparedStatement pstmt1=c.prepareStatement(sql))
			{
				int vehicleSpaceNumber=0;
				pstmt1.setInt(1, floorNumber);
				String[] words=spaceNumber.split("-");
				//System.out.println(words);
				System.out.println(words[1]+" printing the words array");
				vehicleSpaceNumber=Integer.parseInt(words[1]);
				pstmt1.setInt(2, vehicleSpaceNumber);
				System.out.println(spaceNumber+"value inside update vehicle spaces table - Depart action");
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

	private void updateCostSummaryTable() {
		// TODO Auto-generated method stub
		Connection c = null;
		Statement stmt=null;
		try {
				Class.forName("org.postgresql.Driver");
				c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/test2","postgres", "admin");
				c.setAutoCommit(false);
				
				//Creation of Statement.
				stmt=c.createStatement();
				
				String sql="update costsummary set amountcollected=amountcollected+? where vehicletype=?";
				int affectedrows=0;
				try(PreparedStatement pstmt =c.prepareStatement(sql))
				{
					pstmt.setInt(1, payment);
					pstmt.setString(2, vehicleType);
					affectedrows=pstmt.executeUpdate();
				}
				catch(SQLException ex)
				{
					System.out.println(ex.getMessage());
				}
				
				//updating Number of Vehicles.
				sql="update costsummary set numberofvehicles=numberofvehicles+1 where vehicletype=?";
				affectedrows=0;
				try(PreparedStatement pstmt =c.prepareStatement(sql))
				{
					
					pstmt.setString(1, vehicleType);
					affectedrows=pstmt.executeUpdate();
				}
				catch(SQLException ex)
				{
					System.out.println(ex.getMessage());
				}
				
				//Closing All.
				stmt.close();
				c.commit();
				c.close();
			}
			catch (Exception e) {
				e.printStackTrace();
				System.err.println(e.getClass().getName()+": "+e.getMessage());
				System.exit(0);
				}
			System.out.println("Successfully updated Cost Summary Database.");

		
	}

	private void deleteEntryFromActiveTable() {
		// TODO Auto-generated method stub
		Connection c = null;
		Statement stmt=null;
		try {
				Class.forName("org.postgresql.Driver");
				c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/test2","postgres", "admin");
				c.setAutoCommit(false);
				
				//Creation of Statement.
				stmt=c.createStatement();
				String sql="delete from parkinglot where vehicle_no=?";
				int affectedrows=0;
				
				try(PreparedStatement pstmt = c.prepareStatement(sql)){
					
					pstmt.setString(1,vehicleNumber);
					affectedrows = pstmt.executeUpdate();
					
				}
				catch(SQLException ex)
				{
					System.out.println(ex.getMessage());
				}
				//closing all
				stmt.close();
				c.commit();
				c.close();
			}
			catch (Exception e) {
				e.printStackTrace();
				System.err.println(e.getClass().getName()+": "+e.getMessage());
				System.exit(0);
				}
			System.out.println("Successfully deleted entry from Active Database.");

		
	}

	private void updateExitTime() {
		// TODO Auto-generated method stub
		
		Connection c = null;
		Statement stmt=null;
		try {
				Class.forName("org.postgresql.Driver");
				c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/test2","postgres", "admin");
				c.setAutoCommit(false);
				
				//Creation of Statement.
				stmt=c.createStatement();
				String sql="select * from parkinglotarchive where vehicle_no=? and entrytime=?";
				int affectedrows=0;
				
				try(PreparedStatement pstmt = c.prepareStatement(sql)){
					
					pstmt.setString(1,vehicleNumber);
					pstmt.setString(2,entryTime);
					ResultSet rs = pstmt.executeQuery();
					while(rs.next())
					{
						exitTime=rs.getString("exittime");
					}
					System.out.println(vehicleNumber+" " +vehicleType+" "+floorNumber+" "+exitTime);
				}
				catch(SQLException ex)
				{
					System.out.println(ex.getMessage());
				}
				//closing all
				stmt.close();
				c.commit();
				c.close();
			}
				catch (Exception e) {
					e.printStackTrace();
					System.err.println(e.getClass().getName()+": "+e.getMessage());
					System.exit(0);
					}
				System.out.println("Successfully updated Exit Time.");
		System.out.println(exitTime);
		
	}

	private void moveIntoArchivedDatabase() {
		// TODO Auto-generated method stub
		Connection c = null;
		Statement stmt=null;
		try {
				Class.forName("org.postgresql.Driver");
				c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/test2","postgres", "admin");
				c.setAutoCommit(false);
				
				//Creation of Statement.
				stmt=c.createStatement();
				int affectedrows=0;
				String sql="insert into parkinglotarchive(vehicle_no,vehicletype,floornumber,spacenumber,entrytime,exittime)"+" values(?,?,?,0,?,current_timestamp)";
				try(PreparedStatement pstmt = c.prepareStatement(sql)){
					
					pstmt.setString(1,vehicleNumber);
					pstmt.setString(2,vehicleType);
					pstmt.setInt(3,floorNumber);
					//pstmt.setString(4,spaceNumber);
					pstmt.setString(4,entryTime);
					
					affectedrows=pstmt.executeUpdate();
					System.out.println(pstmt);
					System.out.println(affectedrows);
				}
				catch(SQLException ex)
				{
					System.out.println(ex.getMessage());
				}
				//closing all
				stmt.close();
				c.commit();
				c.close();
		} 
		catch (Exception e) {
				e.printStackTrace();
				System.err.println(e.getClass().getName()+": "+e.getMessage());
				System.exit(0);
				}
			System.out.println("Successfully moved into archived Database");
			
		
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
		
				String sqlbike="update vehiclelimit set bike=bike-1 where floornumber=?";
				String sqlcar="update vehiclelimit set car=car-1 where floornumber=?";
				String sqlbus="update vehiclelimit set bus=bus-1 where floornumber=?";
				String sql4=new String();
				if(vehicleType.equals("Bike"))
					sql4=sqlbike;
				else if(vehicleType.equals("Car"))
					sql4=sqlcar;
				else if(vehicleType.equals("Bus"))
					sql4=sqlbus;
				
				int affectedrows=0;
				try(PreparedStatement pstmt=c.prepareStatement(sql4)){
					pstmt.setInt(1, floorNumber);
					System.out.println(pstmt+" Pstatement vehicle limit updation");
					affectedrows=pstmt.executeUpdate();
					System.out.println(affectedrows);
				}
				catch(SQLException ex)
				{
					System.out.println(ex.getMessage()+" from updatevehicle limit 1");
				}
				//closing all
				stmt.close();
				c.commit();
				c.close();
		} 
		catch (Exception e) {
				e.printStackTrace();
				System.err.println(e.getClass().getName()+": "+e.getMessage()+" from update vehicle limit 2");
				System.exit(0);
				}
			System.out.println("Successfully updated Vehicle Limit Table");
		
		
		
		
	}

	private int getValuesFromDatabase() {
		// TODO Auto-generated method stub
		Connection c = null;
		Statement stmt=null;
		try {
			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/test2","postgres", "admin");
			c.setAutoCommit(false);
			
			//Creation of Statement.
			stmt=c.createStatement();
			String sql="select * from parkinglot where vehicle_no=?";
			int affectedrows=0;
			
			try(PreparedStatement pstmt = c.prepareStatement(sql)){
					
					pstmt.setString(1,vehicleNumber);
					ResultSet rs = pstmt.executeQuery();
					while(rs.next())
					{
						vehicleType=rs.getString("vehicletype");
						floorNumber=rs.getInt("floornumber");
						spaceNumber=rs.getString("spacenumber");
						entryTime=rs.getString("entrytime");
					}
				System.out.println(vehicleType+" "+floorNumber);
				}
			catch(SQLException ex)
			{
				System.out.println(ex.getMessage()+" from getvalues1");
			}
			//closing all
			stmt.close();
			c.commit();
			c.close();
		} 
		catch (Exception e) {
				e.printStackTrace();
				System.err.println(e.getClass().getName()+": "+e.getMessage()+" from getvalues2");
				System.exit(0);
				}
			System.out.println("Obtained values from parking lot table Successfully");
		
		return 0;
		}
		
	

	private int calculatePayment(int duration2, String vehicleType2) {
		// TODO Auto-generated method stub
		int costPerHour = 0;
		if(vehicleType2.equals("Bike"))
			costPerHour=100;
		else if(vehicleType2.equals("Car"))
			costPerHour=200;
		if(vehicleType2.equals("Bus"))
			costPerHour=300;
		
		int payment2=costPerHour+(duration2-1)*costPerHour/2;
		setCostInformation("1*"+Integer.toString(costPerHour)+"rs"+"+"+Integer.toString(duration2-1)+"*"+Integer.toString(costPerHour/2)+"rs");
		return payment2;
		
		
		
		//return 0;
	}

	private int calculateDuration(String entryTime2,String exitTime2) {
		// TODO Auto-generated method stub
		
		// Extracting Substrings in entry Time.
		int startHours=Integer.parseInt(entryTime2.substring(11,13));
		int startMinutes=Integer.parseInt(entryTime2.substring(14,16));
		int startSeconds=Integer.parseInt(entryTime2.substring(17,19));
		
		int endHours=Integer.parseInt(exitTime2.substring(11,13));
		int endMinutes=Integer.parseInt(exitTime2.substring(14,16));
		int endSeconds=Integer.parseInt(exitTime2.substring(17,19));
		
		int minutes=(endHours-startHours)*60+(endMinutes-startMinutes)+1;
		
		System.out.println(endHours+ " hours " +endMinutes +" minutes"+endSeconds+ " seconds");
		System.out.println(minutes);
		return minutes;
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
	public String getSpaceNumber() {
		return spaceNumber;
	}
	public String getEntryTime() {
		return entryTime;
	}
	public String getExitTime() {
		return exitTime;
	}

	public int getPayment() {
		return payment;
	}

	public void setPayment(int payment) {
		this.payment = payment;
	}
	
	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getCostInformation() {
		return costInformation;
	}

	public void setCostInformation(String costInformation) {
		this.costInformation = costInformation;
	}
}
