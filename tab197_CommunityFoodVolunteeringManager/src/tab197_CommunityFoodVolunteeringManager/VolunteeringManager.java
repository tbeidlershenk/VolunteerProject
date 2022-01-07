package tab197_CommunityFoodVolunteeringManager;

import java.util.ArrayList;
public class VolunteeringManager {

	private ArrayList<CommunityFoodOrg> organizations;
	private ArrayList<Volunteer> volunteers;
	private int lastOrgSignup;
	
	// Constructor reads the files by calling DataManager static methods
	public VolunteeringManager (String orgsFileName, String volunteersFileName) {
		organizations = DataManager.readCommunityFoodOrgs(orgsFileName);
		volunteers = DataManager.readVolunteers(volunteersFileName);
	}
	
	/** 
	* Method signUpVolunteerToPriorityOrg
	* @param Volunteer object
	* @return boolean true if the Volunteer was signed up, false 
	* 	otherwise. Signed up to the organization most needing help
	*/ 
	public boolean signUpVolunteerToPriorityOrg(Volunteer volunteer) {
		String day = volunteer.getDayAvailable();
		int dayIndex = CommunityFoodOrg.getWeek().indexOf(day);
		double max = 0;
		int index = -1;
		for (int i = 0; i < organizations.size(); i++) {
			if (organizations.get(i).getDailyOpenHours()[dayIndex] != null) {
				if (volunteer.orgMatch(organizations.get(i)) && organizations.get(i) instanceof FoodPantry) {
					int dif = organizations.get(i).getDailyVolunteersNeeded()[dayIndex] - organizations.get(i).getDailyVolunteerSignups()[dayIndex];				
					if (dif > 0) {
						max = dif;
						index = i;
						break;
					}
				} else if (volunteer.orgMatch(organizations.get(i)) && organizations.get(i) instanceof FoodBank){
					FoodBank temp = (FoodBank)organizations.get(i);
					if (temp.dailyDonationsNeeded(volunteer.getDayAvailable()) > 0) {
						max = temp.dailyDonationsNeeded(volunteer.getDayAvailable());
						index = i;
						break;
					}
				}
			} 
		}
		for (int i = index + 1; i < organizations.size(); i++) {
			if (organizations.get(i).getDailyOpenHours()[dayIndex] != null) {
				if (volunteer.orgMatch(organizations.get(i)) && organizations.get(i) instanceof FoodPantry) {
					int dif = organizations.get(i).getDailyVolunteersNeeded()[dayIndex] - organizations.get(i).getDailyVolunteerSignups()[dayIndex];
					if (dif > max) {
						max = dif;
						index = i;	
					}
				} else if (volunteer.orgMatch(organizations.get(i)) && organizations.get(i) instanceof FoodBank) {
					FoodBank temp = (FoodBank)organizations.get(i);
					if (temp.dailyDonationsNeeded(volunteer.getDayAvailable()) > max) {
						max = temp.dailyDonationsNeeded(volunteer.getDayAvailable());
						index = i;
					}
				}
			} 
		}
		if (max > 0) {
			volunteer.signUp(organizations.get(index), volunteer);
			lastOrgSignup = index;
			return true;
		}
		return false;
	}
	
	// SETTERS AND GETTERS BELOW
	
	// Setter and Getter for variable organizations
	public ArrayList<CommunityFoodOrg> getOrganizations () {
		return organizations;
	}
	public void setOrganizations (ArrayList<CommunityFoodOrg> orgs) {
		organizations = orgs;
	}
	
	// Setter and Getter for variable organizations
	public ArrayList<Volunteer> getVolunteers () {
		return volunteers;
	}
	public void setVolunteers (ArrayList<Volunteer> vols) {
		volunteers = vols;
	}
	
	// Getter for CommunityFoodOrg variable in organizations at lastOrgSignup
	public CommunityFoodOrg getOrg () {
		return organizations.get(lastOrgSignup);
	}
}
