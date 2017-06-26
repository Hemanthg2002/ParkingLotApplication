package com.ParkingLot.process;

import com.ParkingLot.dto.floorBean;

public class indexProcess {
	 private int floors=0;
	 floorBean fbean=new floorBean();
	 
	 	public String execute() throws Exception 
	 	{
		
		
			 if(fbean.getFloors()!=0)
				  return "success";
			 else
			 {
				 
				  return "error";
			 }
	   }
	   
	   public int getFloors() {
	      return floors;
	   }

	   public void setFloors(String floors) {
	      this.floors = Integer.parseInt(floors);
	      
	   }

}
