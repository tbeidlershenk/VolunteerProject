package tab197_CommunityFoodVolunteeringManager;

public class FoodPantry extends CommunityFoodOrg {

	int[] dailyVolunteersNeeded, dailyVolunteerSignups;
	
	public FoodPantry (String id, String name, Location loc, TimeFrame[] dailyOH, int[] dailyVN, int[] dailyVS, boolean offersT) {
		super(id, name, loc, dailyOH, offersT);
		dailyVolunteersNeeded = dailyVN;
		dailyVolunteerSignups = dailyVS;
	}
	/** 
	* Method signUpVolunteer, Overrides signUpVolunteer in CommunityFoodOrg class
	* @param Volunteer object
	* @return boolean true if the volunteer was signed up for the food pantry (had enough signup space)
	* or false if there was not enough space left and they were not signed up
	*/ 
	@Override
	public boolean signUpVolunteer (Volunteer volunteer) {
		String day = volunteer.getDayAvailable();
		if (dailyVolunteersNeeded[CommunityFoodOrg.getWeek().indexOf(day)] > 0) {
			dailyVolunteerSignups[CommunityFoodOrg.getWeek().indexOf(day)]++;
			dailyVolunteersNeeded[CommunityFoodOrg.getWeek().indexOf(day)]--;
			return true;
		}
		return false;
	}
	/** 
	* Method cancelVolunteerSignup, Overrides cancelVolunteerSignup in CommunityFoodOrg class
	* @param String dayName
	* @return void, just cancels a volunteers donation on a given day, or prints that the
	* food pantry has no signups yet
	*/ 
	@Override
	public void cancelVolunteerSignup (String dayName) {
		if (dailyVolunteerSignups[CommunityFoodOrg.getWeek().indexOf(dayName)] > 0) {
			dailyVolunteerSignups[CommunityFoodOrg.getWeek().indexOf(dayName)]--;
		} else {
			System.out.println("No volunteer has signed up yet");
		}
	}
	
	/** 
	* Method dailyVolunteerSpotsLeft
	* @param String dayName
	* @return int, number of spots still needing to be filled on dayName
	*/ 
	public int dailyVolunteerSpotsLeft (String dayName) {
		return dailyVolunteersNeeded[week.indexOf(dayName)];
	}
	
	
	// Getter and Setters for variable dailyVolunteersNeeded
	public int[] getDailyVolunteersNeeded () {
		return dailyVolunteersNeeded;
	}
		// Sets the whole array of volunteers needed (unless invalid)
	public void setDailyVolunteersNeeded (int[] val) {
		dailyVolunteersNeeded = CommunityFoodOrg.volunteerValidity(dailyVolunteersNeeded, val);
	}
		// Sets a specific day with a number of volunteers needed
	public void setDailyVolunteersNeeded (int volunteers, String dayName) {
		dailyVolunteersNeeded[week.indexOf(dayName)] = volunteers;
	}
	
	// Getter and Setters for variable dailyVolunteerSignups
	public int[] getDailyVolunteerSignups () {
		return dailyVolunteerSignups;
	}
		// Sets the whole array of volunteers needed (unless invalid)
	public void setDailyVolunteerSignups (int[] val) {
		dailyVolunteerSignups = CommunityFoodOrg.volunteerValidity(dailyVolunteerSignups, val);
	}
		// Sets a specific day with a number of signups
	public void setDailyVolunteerSignups (int signups, String dayName) {
		dailyVolunteerSignups[week.indexOf(dayName)] = signups;
	}
}
