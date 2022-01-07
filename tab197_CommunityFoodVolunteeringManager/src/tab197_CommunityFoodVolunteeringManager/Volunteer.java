package tab197_CommunityFoodVolunteeringManager;

/**
 * Class Volunteer
 * Author: Tobias Beidler-Shenk
 * Created: 10/8/21
 */

public class Volunteer {

	// Class Properties
	private String id, fullName, dayAvailable;
	private int age = 18;
	private Location location;
	private TimeFrame timeAvailable;
	private double distanceAvailable, donation;
	private boolean needsTransportation;
	private CommunityFoodOrg orgVolunteering = null;
	private boolean[] noConflicts = {false, false, false, false};
	
	// Constructor with error checking for variable age
	public Volunteer (String id, String name, int age, Location loc, String dayAv, TimeFrame timeAv, double distAv, boolean needTrans, double donate) {
		this.id = id;
		fullName = name;
		if (age >= 18 && age <= 100) {
			this.age = age;
		}
		location = loc;
		dayAvailable = dayAv;
		timeAvailable = timeAv;
		distanceAvailable = distAv;
		needsTransportation = needTrans;	
		donation = donate;
	}
	
	/** 
	* Method signUp
	* @param CommunityFoodOrg object
	* @return void (signs up Volunteer for 
	* 	the organization unless it is full)
	*/ 
	public void signUp(CommunityFoodOrg org, Volunteer volunteer) {
		if (org.signUpVolunteer(volunteer)) {
			orgVolunteering = org;
			if (org instanceof FoodPantry) {
				FoodPantry fp = (FoodPantry) org;
				System.out.println("Signed up at " + org.getName() + ". Spots left: " + fp.dailyVolunteerSpotsLeft(volunteer.getDayAvailable()));
			} else {
				FoodBank fb = (FoodBank) org;
				System.out.println("Donating at " + org.getName() + ". Donations still needed: " + fb.dailyDonationsNeeded(volunteer.getDayAvailable()));
			}
		} else if (org instanceof FoodPantry) {
			System.out.println("The organization " + org.getName() + " has enough volunteers.");
		} else {
			System.out.println("Not enough donation space left at " + org.getName());
		}
	}
	
	/** 
	* Method cancelSignUp
	* @param nothing
	* @return void (cancels sign-up of the Volunteer)
	*/ 
	public void cancelSignUp () {
		orgVolunteering.cancelVolunteerSignup(dayAvailable);
		orgVolunteering = null;
	}
	
	/** 
	* Method orgMatch
	* @param CommunityFoodOrg object
	* @return boolean true if the Volunteer and CommunityFoodOrg parameter match 
	* 	(same day, matching TimeFrames, transportation lines up, within distance)
	*/ 
	public boolean orgMatch (CommunityFoodOrg org) {
		boolean timeMatch = org.getDailyOpenHours()[CommunityFoodOrg.getWeek().indexOf(dayAvailable)] != null 
				&& org.getDailyOpenHours()[CommunityFoodOrg.getWeek().indexOf(dayAvailable)].timeFrameMatch(timeAvailable);
		if (donation > 0) {
			if (org instanceof FoodBank) {
				return ((!needsTransportation || org.getOffersTransportation()) && org.dayMatch(dayAvailable) 
						&& timeMatch && org.getLocation().distance(location) <= distanceAvailable);
			}
		} else {
			if (org instanceof FoodPantry) {
				return ((!needsTransportation || org.getOffersTransportation()) && org.dayMatch(dayAvailable) 
						&& timeMatch && org.getLocation().distance(location) <= distanceAvailable);
			}
		}
		return false;
	}
	
	/** 
	* Method updateConflicts
	* @param CommunityFoodOrg object
	* @return void (sets the conflicts between the Volunteer 
	* 	and the CommunityFoodOrg parameter)
	*/ 
	public void updateConflicts(CommunityFoodOrg org) {
		noConflicts[0] = !needsTransportation || org.getOffersTransportation();
		noConflicts[1] = org.dayMatch(dayAvailable);
		noConflicts[2] = org.getDailyOpenHours()[CommunityFoodOrg.getWeek().indexOf(dayAvailable)] != null && org.getDailyOpenHours()[CommunityFoodOrg.getWeek().indexOf(dayAvailable)].timeFrameMatch(timeAvailable);
		noConflicts[3] = org.getLocation().distance(location) <= distanceAvailable;
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
	public String getFullName () {
		return fullName;
	}
	public void setFullName (String newName) {
		fullName = newName;
	}
	
	// Getter and Setter for variable age
	public int getAge () {
		return age;
	}
	public void setAge (int newAge) {
		if (newAge >= 18 && newAge <= 100) {
			age = newAge;
		} else {
			age = 18;
		}
	}
	
	// Getter and Setter for variable location
	public Location getLocation () {
		return location;
	}
	public void setLocation (Location newLoc) {
		location = newLoc;
	}
	
	// Getter and Setter for variable dayAvailable
	public String getDayAvailable () {
		return dayAvailable;
	}
	public void setDayAvailable (String newDay) {
		dayAvailable = newDay;
	}
	
	// Getter and Setter for variable timeAvailable
	public TimeFrame getTimeAvailable () {
		return timeAvailable;
	}	
	public void setTimeAvailable (TimeFrame newTime) {
		timeAvailable = newTime;
	}
	
	// Getter and Setter for variable distanceAvailable
	public double getDistanceAvailable () {
		return distanceAvailable;
	}
	public void setDistanceAvailable (double newDist) {
		distanceAvailable = newDist;
	}
	
	// Getter and Setter for variable needsTransportation
	public boolean getNeedsTransportation () {
		return needsTransportation;
	}	
	public void setNeedsTransportation (boolean val) {
		needsTransportation = val;
	}
	
	// Getter and Setter for variable orgVolunteering
	public CommunityFoodOrg getOrgVolunteering () {
		return orgVolunteering;
	}	
	public void setOrgVolunteering (CommunityFoodOrg newOrg) {
		orgVolunteering = newOrg;
	}
	
	public boolean[] getNoConflicts() {
		return noConflicts;
	}
	
	// Getter and Setter for variable donation
	public double getDonation() {
		return donation;
	}	
	public void setDonation (double donate) {
		if (donate >= 0) {
			donation = donate;
		}
	}
	
}
