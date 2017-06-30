package com.ParkingLot.model;

public class User {
	
	//Parameters for storing Values
	private static int numberOfFloors;
	private static int spacesOnFloors;
	
	//Getters and Setters for numberOfFloors
	
	public int getNumberOfFloors() {
		return numberOfFloors;
	}
	public void setNumberOfFloors(int numberOfFloors) {
		User.numberOfFloors = numberOfFloors;
	}
	
	
	//getters and setters for spacesOnFloors
	
	public int getSpacesOnFloors() {
		return spacesOnFloors;
	}
	public void setSpacesOnFloors(int spacesOnFloors) {
		User.spacesOnFloors = spacesOnFloors;
	}
	
}
