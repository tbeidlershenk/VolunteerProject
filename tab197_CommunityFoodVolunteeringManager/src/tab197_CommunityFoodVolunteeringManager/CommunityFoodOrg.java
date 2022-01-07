package tab197_CommunityFoodVolunteeringManager;

import java.util.ArrayList;
import java.util.Arrays;

/** 
 * Class CommunityFoodOrg
 * Author: Tobias Beidler-Shenk
 * Created: 10/8/21
 */

public class CommunityFoodOrg {

	// Class Properties
	private String id;
	private String name;
	private Location location;
	private TimeFrame[] dailyOpenHours;
	private boolean offersTransportation;
	private int[] dailyVolunteersNeeded;
	private int[] dailyVolunteerSignups;
	// Static Method asList of the class Arrays allows me to initialize week as static with values
		// Needs to be static to return in the static method getWeek (line 202)
	protected static ArrayList<String> week = new ArrayList<>(Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"));
	
	// Constructor, taking in values for the class properties
	public CommunityFoodOrg (String id, String name, Location loc, TimeFrame[] dOH, boolean offersT) {	
		this.id = id;
		this.name = name;
		location = loc;
		offersTransportation = offersT;
		
		// Error checking
		dailyOpenHours = CommunityFoodOrg.timeValidity(dailyOpenHours, dOH);
		
	}
	
	/** 
	* Method signUpVolunteer
	* @param String day (day of the week)
	* @return boolean true
	*/ 
	public boolean signUpVolunteer (Volunteer volunteer) {
		System.out.println("Checking the necessary information for signing up " + volunteer.getFullName() + " for helping " + name + " on " + volunteer.getDayAvailable());
		return true;
	}
	
	/** 
	* Method cancelVolunteerSignup
	* @param String day (day of the week)
	* @return void (just prints that the cancellation is occurring)
	*/ 
	public void cancelVolunteerSignup (String day) {
		System.out.println("Canceling volunteer signup for " + name + " on " + day);
	}
	
	/** 
	* Method dailyVolunteerSpotsLeft
	* @param String day (day of the week)
	* @return int (number of spots left 
	* 	at the organization on that day)
	*/ 
	public int dailyVolunteerSpotsLeft (String day) {
		return dailyVolunteersNeeded[week.indexOf(day)] - dailyVolunteerSignups[week.indexOf(day)];
	}
	
	/** 
	* Method volunteerValidity
	* @param String day (day of the week)
	* @return true if open, false if not open
	*/ 
	public boolean dayMatch (String day) {
		return dailyOpenHours[week.indexOf(day)] != null;
	}
	/** 
	* Method volunteerValidity
	* @param int[] object (attribute of the class)
	* @param int[] object (sent into the constructor)
	* @return int[] object (attribute of the class, modified 
	* 	if the constructor input is of valid size and values)
	*/ 
	public static int[] volunteerValidity (int[] value, int[] temp) {
		int[] defaultArray = {0,0,0,0,0,0,0};
		value = defaultArray;
		boolean valid = true;
		for (int i = 0; i < temp.length; i++) {
			if (temp[i] < 0 || temp.length != 7) {
				valid = false;
				break;
			}
		}
		if (valid) {
			value = temp;
		}
		return value;
	}
	
	/** 
	* Method timeValidity 
	* @param Array of TimeFrame objects (attribute of the class)
	* @param Array of TimeFrame objects (sent into the constructor)
	* @return Array of TimeFrame objects (attribute of the class, 
	* 		modified if the constructor input is of valid size)
	*/ 
	public static TimeFrame[] timeValidity (TimeFrame[] value, TimeFrame[] temp) {
		TimeFrame[] defaultArray = {null, null, null, null, null, null, null};
		value = defaultArray;
		if (temp.length == 7) {
			value = temp;
		}
		return value;
	}
	
	// GETTERS AND SETTERS BELOW
	
	// Getter and Setter for variable id
	public String getId () {
		return id;
	}	
	public void setId (String newId) {
		id = newId;
	}
	
	// Getter and Setter for variable name
	public String getName () {
		return name;
	}	
	public void setName (String newName) {
		name = newName;
	}	
	
	// Getter and Setter for variable location
	public Location getLocation () {
		return location;
	}	
	public void setLocation (Location newLoc) {
		location = newLoc;
	}
	
	// Getter and Setters for variable dailyOpenHours
	public TimeFrame[] getDailyOpenHours () {
		return dailyOpenHours;
	}
		// Sets the whole array of TimeFrames
	public void setDailyOpenHours (TimeFrame[] newDOH) {
		dailyOpenHours = newDOH;
	}
		// Sets a specific day in dailyOpenHours with a TimeFrame
	public void setDailyOpenHours (TimeFrame time, String day) {
		dailyOpenHours[week.indexOf(day)] = time;
	}
	// Getter and Setters for variable dailyVolunteersNeeded
	public int[] getDailyVolunteersNeeded () {
		return dailyVolunteersNeeded;
	}
		// Sets the whole array of volunteers needed (unless invalid)
	public void setDailyVolunteersNeeded (int[] val) {
		dailyVolunteersNeeded = CommunityFoodOrg.volunteerValidity(dailyVolunteersNeeded, val);
	}
		// Sets a specific day with a set number of volunteers needed
	public void setDailyVolunteersNeeded (int volunteers, String day) {
		dailyVolunteersNeeded[week.indexOf(day)] = volunteers;
	}
	
	// Getter and Setters for variable dailyVolunteerSignups
	public int[] getDailyVolunteerSignups () {
		return dailyVolunteerSignups;
	}
		// Sets the whole array of volunteers needed (unless invalid
	public void setDailyVolunteerSignups (int[] val) {
		dailyVolunteerSignups = CommunityFoodOrg.volunteerValidity(dailyVolunteerSignups, val);
	}
		// Sets a specific day with a set number of volunteer sign-ups
	public void setDailyVolunteerSignups(int volunteers, String day) {
		dailyVolunteerSignups[week.indexOf(day)] = volunteers;
	}
	
	// Getter and Setter for variable offersTransportation
	public boolean getOffersTransportation () {
		return offersTransportation;
	}	
	public void setOffersTransportation (boolean val) {
		offersTransportation = val;
	}
	
	// Getter for variable week
	public static ArrayList<String> getWeek () {
		return week;
	}
}
