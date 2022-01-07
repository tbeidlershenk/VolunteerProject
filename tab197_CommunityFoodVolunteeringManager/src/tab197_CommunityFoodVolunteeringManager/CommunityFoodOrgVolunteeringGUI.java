package tab197_CommunityFoodVolunteeringManager;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

public class CommunityFoodOrgVolunteeringGUI {
	
	//Define the attributes of the GUI class here
	public ArrayList<Volunteer> volunteersList = new ArrayList<Volunteer>();
	public String orgFile = "data\\community_food_organizations.txt";
	public String volFile = "data\\volunteers.txt";
	public Location loc;
	public int age;
	public double donate, dist;
	public TimeFrame time;
	public String[] week = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
	
	//Attributes related to the graphical elements
	public JFrame frmMainWindow;
	public JLabel lblFullName, lblAge, lblDistance1, lblDistance2, lblTransportation, lblAvailable, 
		lblFrom, lblTo, lblColon1, lblColon2, lblLatitude, lblLongitude, lblAddress, lblDonation, lblCaption;
	public JTextArea txtFullName, txtAge, txtDistance, txtNumberInput1, txtNumberInput2, 
		txtNumberInput3, txtNumberInput4, txtLatitude, txtLongitude, txtAddress, txtDonation;
	public JCheckBox chkTransportation;
	public JComboBox cboAvailable;
	public JButton btnAdd, btnSignUp;
	public JList<String> list;
	public CommunityFoodOrg[] orgs;
	public ArrayList<CommunityFoodOrg> organizations;
	
	public CommunityFoodOrgVolunteeringGUI() {
		
		initializeComponents();
		
		btnAdd.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e) {
        		boolean valid = true;
        		try {
        			age = Integer.parseInt(txtAge.getText());
        			dist = Double.parseDouble(txtDistance.getText());
        			int hs = Integer.parseInt(txtNumberInput1.getText());
        			int ms = Integer.parseInt(txtNumberInput2.getText());
        			int he = Integer.parseInt(txtNumberInput3.getText());
        			int me = Integer.parseInt(txtNumberInput4.getText());
        			double lat = Double.parseDouble(txtLatitude.getText());
        			double lon = Double.parseDouble(txtLongitude.getText());
        			donate = Double.parseDouble(txtDonation.getText());
        			time = new TimeFrame(hs, ms, he, me);
        			loc = new Location(lat, lon, "", "", "", "");
        		} catch (Exception e1){
        			valid = false;
        			System.out.println(e1.getMessage());
        		}
        		if (txtFullName.getText().length() > 0 && txtAddress.getText().length() > 0 && valid) {
        			volunteersList.add(new Volunteer("" + (volunteersList.size() + 1), txtFullName.getText(), age, 
        					loc, week[cboAvailable.getSelectedIndex()], time, dist, chkTransportation.isSelected(), donate));
        			frmMainWindow.add(btnSignUp);
        			System.out.println("\n\n ----------------------------- Volunteer " + volunteersList.get(volunteersList.size()-1).getFullName() + " -----------------------------");
        			CommunityFoodVolunteeringTest.printConflicts(volunteersList.get(volunteersList.size()-1), organizations);
        			orgs = selectionSortOrgs(organizations);
        			String[] orgNames = new String[orgs.length];
        			int count = 0;
        			String day = volunteersList.get(volunteersList.size()-1).getDayAvailable();
        			
        			// Updates the organizations that will be displayed in the JList
        			while (orgs[count] != null) {
        				if (orgs[count] instanceof FoodBank) {
        					FoodBank fb = (FoodBank) orgs[count];
        					if (fb.dailyDonationsNeeded(day) >= volunteersList.get(volunteersList.size()-1).getDonation()) orgNames[count] = orgs[count].getName();

        				} else {
        					FoodPantry fp = (FoodPantry) orgs[count];
        					if (fp.dailyVolunteerSpotsLeft(day) > 0) orgNames[count] = orgs[count].getName();
        				}
        				
        				count++;
        			}
        			
        			// Refreshes the window with the updated JList (passing in new orgNames object)
        			if (list != null) frmMainWindow.remove(list);
        			list = new JList(orgNames);
        			list.setBounds(15, 220, 300, 180);
        			frmMainWindow.setVisible(false);
        			frmMainWindow.add(list);
        			frmMainWindow.setVisible(true);
        			
        		} else {
        			JOptionPane.showMessageDialog(null, "Invalid input!","Error", JOptionPane.ERROR_MESSAGE);
        		}
            }
        });
		addComponents();
		frmMainWindow.setVisible(true);	
		
		btnSignUp.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e) {
        		int index = list.getSelectedIndex();
        		if (index > -1) {
        			volunteersList.get(volunteersList.size()-1).signUp(orgs[index], volunteersList.get(volunteersList.size()-1));
        			organizations.set(organizations.indexOf(orgs[index]), orgs[index]);
        			frmMainWindow.remove(btnSignUp);
        		} else {
        			System.out.println("Please select an organization before clicking sign up.");
        		}
        		
        		
        	}
		});
	}

	// Selection Sort
	public CommunityFoodOrg[] selectionSortOrgs (ArrayList<CommunityFoodOrg> temp) {
		CommunityFoodOrg[] orgs = new CommunityFoodOrg[temp.size()];
		String day = volunteersList.get(volunteersList.size()-1).getDayAvailable();
		int count = 0;
		if (volunteersList.get(volunteersList.size()-1).getDonation() > 0) {
			for (int i = 0; i < temp.size(); i++) {
				if (temp.get(i) instanceof FoodBank && volunteersList.get(volunteersList.size()-1).orgMatch(temp.get(i))) {
					orgs[count] = temp.get(i);
					count++;
				}	
			}
			for (int i = 0; i < count-1; i++) {
				int minIndex = i;
				for (int j = i+1; j < count; j++) {
					FoodBank curMax = (FoodBank) orgs[minIndex];
					FoodBank fb = (FoodBank) orgs[j];
					if (fb.dailyDonationsNeeded(day) > curMax.dailyDonationsNeeded(day)) {
						minIndex = j;
					}
				}
				FoodBank fb = (FoodBank) orgs[minIndex];
				orgs[minIndex] = orgs[i];
				orgs[i] = fb;				
			}
		} else {
			for (int i = 0; i < temp.size(); i++) {
				if (temp.get(i) instanceof FoodPantry && volunteersList.get(volunteersList.size()-1).orgMatch(temp.get(i))) {
					orgs[count] = temp.get(i);
					count++;
				}	
			}	
			for (int i = 0; i < count-1; i++) {
				int minIndex = i;
				for (int j = i+1; j < count; j++) {
					FoodPantry curMax = (FoodPantry) orgs[minIndex];
					FoodPantry fp = (FoodPantry) orgs[j];
					if (fp.dailyVolunteerSpotsLeft(day) > curMax.dailyVolunteerSpotsLeft(day)) {
						minIndex = j;
					}
				}
				FoodPantry fp = (FoodPantry) orgs[minIndex];
				orgs[minIndex] = orgs[i];
				orgs[i] = fp;				
			}
			
		}
		return orgs;	
	}
	
	/** 
	* Method initializeComponents
	* No parameters or returns, this method initializes all components of the GUI
	*/ 
	public void initializeComponents () {
		// Read the CommunityFoodOrg file
		organizations = DataManager.readCommunityFoodOrgs(orgFile);
		
		//Create the frame
		frmMainWindow = new JFrame("Community Food Org Volunteering");
		frmMainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmMainWindow.setResizable(false);
        frmMainWindow.setBounds(200,200,700,500);
        frmMainWindow.setLayout(null);
		
        // Initializes components and sets their sizes
		lblFullName = new JLabel("Full Name");
		lblFullName.setBounds(15,0,100,50);
		txtFullName = new JTextArea("Please type your name here ...");
		txtFullName.setBounds(100,15, 200, 20);
		lblAge = new JLabel("Age");
		lblAge.setBounds(15,30,100,50);
		txtAge = new JTextArea("Please type your age here ...");
		txtAge.setBounds(100, 45, 200, 20);
		lblDistance1 = new JLabel("Availability up to");
		lblDistance1.setBounds(15,60,100,50);
		txtDistance = new JTextArea();
		txtDistance.setBounds(130,75,50,20);
		lblDistance2 = new JLabel("miles");
		lblDistance2.setBounds(200,60,100,50);
		lblTransportation = new JLabel("Needs Transportation?");
		lblTransportation.setBounds(15,90,150,50);
		chkTransportation = new JCheckBox();
		chkTransportation.setBounds(150,105,20,20);
		lblAvailable = new JLabel("Available on:");
		lblAvailable.setBounds(15,120,150,50);
		cboAvailable = new JComboBox(week);
		cboAvailable.setBounds(100,135,100,20);
		lblFrom = new JLabel("From");
		lblFrom.setBounds(15,150,150,50);
		txtNumberInput1 = new JTextArea();
		txtNumberInput1.setBounds(50,165,20,20);
		lblColon1 = new JLabel(":");
		lblColon1.setBounds(75,150,150,50);
		txtNumberInput2 = new JTextArea();
		txtNumberInput2.setBounds(80,165,20,20);
		lblTo = new JLabel("to");
		lblTo.setBounds(110,150,150,50);
		txtNumberInput3 = new JTextArea();
		txtNumberInput3.setBounds(130,165,20,20);
		lblColon2 = new JLabel(":");
		lblColon2.setBounds(155,150,150,50);
		txtNumberInput4 = new JTextArea();
		txtNumberInput4.setBounds(160,165,20,20);
		btnAdd = new JButton("Add Volunteer");
		btnAdd.setBounds(325,140,125, 40);
		lblLatitude = new JLabel("Latitude location");
		lblLatitude.setBounds(320,0,100,50);
		lblLongitude = new JLabel("Longitude location");
		lblLongitude.setBounds(320,30,130,50);
		txtLatitude = new JTextArea();
		txtLatitude.setBounds(440, 15, 230, 20);
		txtLongitude = new JTextArea();
		txtLongitude.setBounds(440, 45, 230, 20);
		lblAddress = new JLabel("Address");
		lblAddress.setBounds(320,60,100,50);
		txtAddress = new JTextArea();
		txtAddress.setBounds(440, 75, 230, 20);
		lblDonation = new JLabel("Donation (lbs)");
		lblDonation.setBounds(320,90,100,50);
		txtDonation = new JTextArea(" 0 if you plan to volunteer for a Food Pantry");
		txtDonation.setBounds(440, 105, 230, 20);
		btnSignUp = new JButton("Sign Up");
		btnSignUp.setBounds(325,250,125,40);
		lblCaption = new JLabel("(Sign-Up information printed to console)");
		lblCaption.setBounds(325,350,300,40);
		list = new JList();
		list.setBounds(15, 220, 300, 180);
	}
	
	/** 
	* Method addComponents
	* No parameters or returns, this method adds all the components to the GUI
	*/ 
	public void addComponents () {
		//Add the graphical elements to the frame
		frmMainWindow.add(lblFullName);
		frmMainWindow.add(txtFullName);
		frmMainWindow.add(lblAge);
		frmMainWindow.add(txtAge);
		frmMainWindow.add(lblDistance1);
		frmMainWindow.add(txtDistance);
		frmMainWindow.add(lblDistance2);
		frmMainWindow.add(lblTransportation);
		frmMainWindow.add(chkTransportation);
		frmMainWindow.add(lblAvailable);
		frmMainWindow.add(cboAvailable);
		frmMainWindow.add(lblFrom);
		frmMainWindow.add(txtNumberInput1);
		frmMainWindow.add(lblColon1);
		frmMainWindow.add(txtNumberInput2);
		frmMainWindow.add(lblTo);
		frmMainWindow.add(txtNumberInput3);
		frmMainWindow.add(lblColon2);
		frmMainWindow.add(txtNumberInput4);
		frmMainWindow.add(btnAdd);
		frmMainWindow.add(lblLatitude);
		frmMainWindow.add(txtLatitude);
		frmMainWindow.add(lblLongitude);
		frmMainWindow.add(txtLongitude);
		frmMainWindow.add(lblAddress);
		frmMainWindow.add(txtAddress);
		frmMainWindow.add(lblDonation);
		frmMainWindow.add(txtDonation);
		frmMainWindow.add(lblCaption);
		frmMainWindow.add(btnSignUp);
		frmMainWindow.add(list);
	}
}
