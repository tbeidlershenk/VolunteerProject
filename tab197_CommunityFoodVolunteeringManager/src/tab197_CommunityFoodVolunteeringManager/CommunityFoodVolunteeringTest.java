package tab197_CommunityFoodVolunteeringManager;

/**
 * Class CommunityFoodVolunteeringTest
 * Author: Tobias Beidler-Shenk
 * Created: 10/8/21
 */

import java.util.ArrayList;

public class CommunityFoodVolunteeringTest {
	
	public static void main (String[] args) {
		String orgFile = "data\\community_food_organizations.txt";
		String volFile = "data\\volunteers.txt";
		CommunityFoodOrgVolunteeringGUI display = new CommunityFoodOrgVolunteeringGUI();
	}
	
	/** 
	* Method printConflicts
	* @param Volunteer object, ArrayList<CommunityFoodOrg> object
	* @return void (prints all conflicts the Volunteer has with each organization)
	*/ 
	public static void printConflicts (Volunteer vol, ArrayList<CommunityFoodOrg> orgs) {
		String[] msgs = new String[4];
		msgs[0] = "	" + vol.getFullName() + " needs transportation, but not offered";
		msgs[1] = "	Volunteering not offered on " + vol.getDayAvailable();
		msgs[2] = "	Volunteering not offered from " + vol.getTimeAvailable().getHourStart() + ":" + vol.getTimeAvailable().getMinuteStart() + " to " 
				+ vol.getTimeAvailable().getHourEnd() + ":" + vol.getTimeAvailable().getMinuteEnd();
		msgs[3] = "	" + vol.getFullName() + " lives ";
		for (int i = 0; i < orgs.size(); i++) {
			if ((orgs.get(i) instanceof FoodBank && vol.getDonation() > 0) || (orgs.get(i) instanceof FoodPantry && vol.getDonation() == 0)) {	
				vol.updateConflicts(orgs.get(i));
				String orgHeader = "- " + orgs.get(i).getName() + " ";
				while (orgHeader.length() < 57) {
					orgHeader = orgHeader + "-";
				}
				System.out.println(orgHeader);
				int count = 0;
				for (int j = 0; j < vol.getNoConflicts().length; j++) {
					if (!vol.getNoConflicts()[j] && j != 3) {
						System.out.println(msgs[j]);
						count++;
					} else if (!vol.getNoConflicts()[j] && j == 3) {
						int excess = (int)(vol.getLocation().distance(orgs.get(i).getLocation()) - vol.getDistanceAvailable());
						System.out.println(msgs[j] + excess + " miles too far from " + orgs.get(i).getName());
						count++;
					}
				}
				if (count == 0) {
					System.out.println("	" + vol.getFullName() + " can work at " + orgs.get(i).getName());
				}	
			}
		}
		System.out.println();
	}
}
