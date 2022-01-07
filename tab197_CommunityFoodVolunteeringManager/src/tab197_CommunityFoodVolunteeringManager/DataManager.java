package tab197_CommunityFoodVolunteeringManager;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class DataManager {
	
	/** 
	* Method readVolunteers
	* @param String fileName (text file)
	* @return ArrayList<Volunteer> with all 
	* 	the volunteers specified in the file
	*/ 
	public static ArrayList<Volunteer> readVolunteers (String fileName) {
		ArrayList<Volunteer> volunteers = new ArrayList<Volunteer>();
		try {
		    FileReader fr = new FileReader(fileName); 
		    BufferedReader br = new BufferedReader(fr); 	    
		    String line = br.readLine();
		    while(line != null) {
		    	 String[] volData = line.split(";"); 
		    	 Location loc = new Location(Double.parseDouble(volData[4]), Double.parseDouble(volData[5]), volData[6], volData[7], volData[8], volData[9]);
		    	 TimeFrame time = DataManager.separateHoursFromMinutes(volData[11], volData[12]);
		    	 volunteers.add(new Volunteer(volData[0], volData[2] + " " + volData[1], Integer.parseInt(volData[3]), loc, volData[10], time, Double.parseDouble(volData[13]), volData[14].equals("yes"), Double.parseDouble(volData[15])));
		    	 line = br.readLine();
		    }
		    br.close();
		    fr.close();
		} catch (IOException e) { 
		    System.out.println(e.getMessage());
		}
		return volunteers;
	}
	
	/** 
	* Method readCommunityFoodOrgs
	* @param String fileName (text file)
	* @return ArrayList<CommunityFoodOrg> with all 
	* 	the organizations specified in the file
	*/ 
	public static ArrayList<CommunityFoodOrg> readCommunityFoodOrgs (String fileName) {
		ArrayList<CommunityFoodOrg> organizations = new ArrayList<CommunityFoodOrg>();
		try {
		    FileReader fr = new FileReader(fileName); 
		    BufferedReader br = new BufferedReader(fr); 	    
		    String line = br.readLine();
		    while(line != null) {
		    	 String[] orgData = line.split(";"); 
		    	 TimeFrame[] times = {null, null, null, null, null, null, null};
		    	 if (orgData[0].equals("Food Bank")) {
			    	 double[] dailyDS = {0,0,0,0,0,0,0};			    	 
			    	 for (int i = 11; i < orgData.length; i++) {
			    		 String[] temp = orgData[i].split("@");
			    		 int dayIndex = CommunityFoodOrg.getWeek().indexOf(temp[0]);
			    		 times[dayIndex] = DataManager.separateHoursFromMinutes(temp[1], temp[2]);
			    	 }
			    	 Location loc = new Location(Double.parseDouble(orgData[3]), Double.parseDouble(orgData[4]), orgData[5], orgData[6], orgData[7], orgData[8]);
			    	 organizations.add(new FoodBank(orgData[1], orgData[2], loc, times, Double.parseDouble(orgData[10]), dailyDS, orgData[9].equals("yes")));
		    	 } else {
		    		 int[] volNeeded = {0,0,0,0,0,0,0};
		    		 int[] volSignups = {0,0,0,0,0,0,0};
		    		 for (int i = 10; i < orgData.length; i++) {
			    		 String[] temp = orgData[i].split("@");
			    		 int dayIndex = CommunityFoodOrg.getWeek().indexOf(temp[0]);
			    		 times[dayIndex] = DataManager.separateHoursFromMinutes(temp[1], temp[2]);
			    		 volNeeded[dayIndex] = Integer.parseInt(temp[3]);
			    	 }
		    		 Location loc = new Location(Double.parseDouble(orgData[3]), Double.parseDouble(orgData[4]), orgData[5], orgData[6], orgData[7], orgData[8]);
		    		 organizations.add(new FoodPantry(orgData[1], orgData[2], loc, times, volNeeded, volSignups, orgData[9].equals("yes")));
		    	 }
		    	 line = br.readLine();
		    }
		    br.close();
		    fr.close();
		} catch (IOException e) { 
		    System.out.println(e.getMessage());
		}
		return organizations;
	}
	
	/** 
	* Method separateHoursFromMinutes
	* @param String startTime, String endTime
	* @return TimeFrame object representing 
	* 	the hours and minutes of the strings
	*/ 
	public static TimeFrame separateHoursFromMinutes (String startTime, String endTime) {
		String[] splitStartTime = startTime.split(":");
   	 	String[] splitEndTime = endTime.split(":");
		return (new TimeFrame(Integer.parseInt(splitStartTime[0]), Integer.parseInt(splitStartTime[1]), 
				Integer.parseInt(splitEndTime[0]), Integer.parseInt(splitEndTime[1])));
	}

}
