package tab197_CommunityFoodVolunteeringManager;

import java.lang.Math;

/** 
 * Class Location
 * Author: Tobias Beidler-Shenk
 * Created: 10/8/21
 */

public class Location {

	// Class Properties
	public final double EARTH_RADIUS = 3958.8;
	private double latitude = 0;
	private double longitude = 0;
	private String address;
	private String city;
	private String state;
	private String zipCode;
	
	// Constructor with error checking for latitude/longitude
	public Location (double lat, double lon, String addr, String city, String state, String zip) {
		if (lat >= -90 && lat <= 90) {
			latitude = lat;
		}
		if (lon >= -180 && lon <= 180) {
			longitude = lon;
		}
		longitude = lon;
		address = addr;
		this.city = city;
		this.state = state;
		zipCode = zip;
	}
	
	/** 
	* Method distance
	* @param Location object
	* @return distance between the Location object 
	* 	parameter and the location object being called on
	*/ 
	public double distance (Location loc) {
		double deltaLat = Math.toRadians(loc.getLatitude()) - Math.toRadians(latitude);
		double deltaLon = Math.toRadians(loc.getLongitude()) - Math.toRadians(longitude);
		double a = Math.pow(Math.sin(deltaLat/2), 2) + Math.cos(Math.toRadians(latitude)) * 
				Math.cos(Math.toRadians(loc.getLatitude())) * Math.pow(Math.sin(deltaLon/2), 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		double d = EARTH_RADIUS * c;
		return d;
	}
	
	
	// GETTERS AND SETTERS BELOW
	
	// Getter and Setter for variable longitude
	public double getLatitude () {
		return latitude;
	}
	public void setLatitude (double lat) {
		if (lat >= -90 && lat <= 90) {
			latitude = lat;
		} else {
			latitude = 0;
		}
	}
	
	// Getter and Setter for variable longitude
	public double getLongitude () {
		return longitude;
	}
	public void setLongitude (double lon) {
		if (lon >= -180 && lon <= 180) {
			longitude = lon;
		} else {
			longitude = 0;
		}
	}
	
	// Getter and Setter for variable address
	public String getAddress () {
		return address;
	}
	public void setAddress (String newAddr) {
		address = newAddr;
	}
	
	// Getter and Setter for variable city
	public String getCity () {
		return city;
	}
	public void setCity (String newCity) {
		city = newCity;
	}
	
	// Getter and Setter for variable state
	public String getState () {
		return state;
	}
	public void setState (String newState) {
		state = newState;
	}
	
	// Getter and Setter for variable zipCode
	public String getZipCode () {
		return zipCode;
	}
	public void setZipCode (String newZip) {
		zipCode = newZip;
	}
}
