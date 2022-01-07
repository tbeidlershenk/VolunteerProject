package tab197_CommunityFoodVolunteeringManager;

/**
 * Class TimeFrame
 * Author: Tobias Beidler-Shenk
 * Created: 10/8/21
 */

public class TimeFrame {

	// Class Properties
	private int hourStart = 8;
	private int minuteStart = 0;
	private int hourEnd = 8;
	private int minuteEnd = 0;
	
	// Constructor, taking in values for hourStart, minuteStart, hourEnd, minuteEnd
	public TimeFrame (int hs, int ms, int he, int me) {
		// Error checking for valid values
		if (hs >= 0 && hs <= 23) {
			hourStart = hs;
		}
		if (ms >= 0 && ms <= 59) {
			minuteStart = ms;
		}
		if (he >= 0 && he <= 23) {
			hourEnd = he;
		}
		if (me >= 0 && me <= 59) {
			minuteEnd = me;
		}
	}
	
	// Overloaded constructor, taking in values for hourStart and hourEnd only
	public TimeFrame (int hs, int he) {
		// Error checking for valid values
		if (hs >= 0 && hs <= 23) {
			hourStart = hs;
		}
		if (he >= 0 && he <= 23) {
			hourEnd = he;
		}
	}
	
	/** 
	* Method timeFrameMatch
	* @param TimeFrame object
	* @return boolean true if the parameter falls within the 
	* 	timeFrame object its being called on, false otherwise
	*/ 
	public boolean timeFrameMatch (TimeFrame time) {
		double[] times = {hourStart + minuteStart/60.0, hourEnd + minuteEnd/60.0, 
				time.getHourStart() + time.getMinuteStart()/60.0, time.getHourEnd() + time.getMinuteEnd()/60.0};
		return times[2] >= times[0] && times[3] <= times[1];
	}
	
	// GETTERS AND SETTERS BELOW
	
	// Getter and Setter for variable hourStart
	public int getHourStart () {
		return hourStart;
	}
	public void setHourStart (int hs) {
		if (hs >= 0 && hs <= 23) {
			hourStart = hs;
		} else {
			hourStart = 8;
		}
	}
	
	// Getter and Setter for variable minuteStart
	public int getMinuteStart () {
		return minuteStart;
	}
	public void setMinuteStart (int ms) {
		if (ms >= 0 && ms <= 59) {
			minuteStart = ms;
		} else {
			minuteStart = 0;
		}
	}
	
	// Getter and Setter for variable hourEnd
	public int getHourEnd () {
		return hourEnd;
	}
	public void setHourEnd (int he) {
		if (he >= 0 && he <= 23) {
			hourEnd = he;
		} else {
			hourEnd = 8;
		}
	}
	
	// Getter and Setter for variable minuteEnd
	public int getMinuteEnd () {
		return minuteEnd;
	}
	public void setMinuteEnd (int me) {
		if (me >= 0 && me <= 59) {
			minuteEnd = me;
		} else {
			minuteEnd = 0;
		}
	}
}
