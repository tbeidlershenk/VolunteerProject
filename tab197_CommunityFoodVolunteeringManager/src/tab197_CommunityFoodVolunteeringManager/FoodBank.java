package tab197_CommunityFoodVolunteeringManager;

public class FoodBank extends CommunityFoodOrg {
	
	private double maxCapacity;
	private double[] dailyDonationSignups;
	private double[] dailyDonationsNeeded = new double[7];
	
	public FoodBank (String id, String name, Location loc, TimeFrame[] dailyOH, double maxCap, double[] dailyDS, boolean offersT) {
		super(id, name, loc, dailyOH, offersT);
		maxCapacity = maxCap;
		dailyDonationSignups = dailyDS;	
		for (int i = 0; i < 7; i++) {
			dailyDonationsNeeded[i] = maxCapacity;
		}
	}
	/** 
	* Method signUpVolunteer, Overrides signUpVolunteer in CommunityFoodOrg class
	* @param Volunteer object
	* @return boolean true if the volunteer was signed up for the food bank (had enough donation space)
	* or false if there was not enough donation space and they were not signed up
	*/ 
	@Override
	public boolean signUpVolunteer (Volunteer volunteer) {	
		int dayIndex = CommunityFoodOrg.getWeek().indexOf(volunteer.getDayAvailable());
		if (volunteer.getDonation() <= dailyDonationsNeeded[dayIndex]) {
			dailyDonationsNeeded[dayIndex] -= volunteer.getDonation();
			dailyDonationSignups[dayIndex] += volunteer.getDonation();
			return true;
		}
		return false;
	}
	
	/** 
	* Method cancelVolunteerSignup, Overloads cancelVolunteerSignup in CommunityFoodOrg class
	* @param String dayName, double donation
	* @return void, just cancels a volunteers donation on a given day
	*/ 
	public void cancelVolunteerSignup (String dayName, double donation) {
		int dayIndex = CommunityFoodOrg.getWeek().indexOf(dayName);
		dailyDonationsNeeded[dayIndex] += donation;
	}
	
	/** 
	* Method dailyDonationsNeeded
	* @param String dayName
	* @return double donation that is needed on dayName
	*/ 
	public double dailyDonationsNeeded (String dayName) {		
		int dayIndex = CommunityFoodOrg.getWeek().indexOf(dayName);
		return dailyDonationsNeeded[dayIndex];
	}
	
	/** 
	* Method setDailyDonationSignups
	* @param double donations, String dayName
	* @return void, sets the total amount donated on a given day
	*/ 
	public void setDailyDonationSignups (double donations, String dayName) {
		int dayIndex = CommunityFoodOrg.getWeek().indexOf(dayName);
		dailyDonationSignups[dayIndex] = donations;
	}
	
	// Getters and Setters for variable maxCapacity
	public double getMaxCapacity () {
		return maxCapacity;
	}
	public void setMaxCapacity (double newCap) {
		maxCapacity = newCap;
	}
	
	// Setter for variable dailyDonationsNeeded
	public void setDailyDonationsNeeded (double donations, String dayName) {
		int dayIndex = CommunityFoodOrg.getWeek().indexOf(dayName);
		if (donations > 0) {
			dailyDonationsNeeded[dayIndex] = donations;
		}
	}
}
