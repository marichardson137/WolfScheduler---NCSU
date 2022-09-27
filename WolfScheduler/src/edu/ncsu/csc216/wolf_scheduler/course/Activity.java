package edu.ncsu.csc216.wolf_scheduler.course;


/**
 * Abstract class used to handle basic scheduling and naming of activities.
 * Keeps track of two constants for time values, as well as the activity's
 * title, meetingDays (as a String), startTime, and endTime (as integers).
 * The Course and Event classes inherit the Activity class to make use of these
 * basic scheduling fields and methods as well as two abstract methods for 
 * displaying their data.
 * 
 * @author Michael Richardson
 *
 */
public abstract class Activity implements Conflict {

	/** Total hours in a day */
	private static final int UPPER_HOUR = 24;
	/** Total minutes in an hour */
	private static final int UPPER_MINUTE = 60;
	/** Activity's title. */
	private String title;
	/** Activity's meeting days */
	private String meetingDays;
	/** Activity's starting time */
	private int startTime;
	/** Activity's ending time */
	private int endTime;

	/**
	 * Constructs the four private fields of Activity through a call from the child class
	 * @param title			name of Activity
	 * @param meetingDays	meeting days for Activity as series of chars
	 * @param startTime		start time of the Activity as an integer (military)
	 * @param endTime		end time of the Activity as an integer (military)
	 */
    public Activity(String title, String meetingDays, int startTime, int endTime) {
        super();
        setTitle(title);
        setMeetingDaysAndTime(meetingDays, startTime, endTime);
    }
    
    /**
     * Returns a string array of a few private fields in a child class as well as
     * those provided in this class. Used to populate the rows of the course
     * catalog and student schedule in the GUI
     * @return shortDisplay string array of some fields
     */
    public abstract String[] getShortDisplayArray();
    
    /**
     * Returns a string array of all private fields in a child class as well as
     * those provided in this class. Used to print all information for an
     * activity when displaying the final schedule in the GUI
     * @return longDisplay string array of all fields
     */
    public abstract String[] getLongDisplayArray();

    /**
     * Returns true if the provided Activity is a duplicate in the
     * schedule and false if it was not found in the list.
     * @param activity the activity to check
     * @return isDuplicate boolean
     */
    public abstract boolean isDuplicate(Activity activity);
    
	/**
	 * Returns the title of the activity
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title of the activity. If the title is null or an empty string an
	 * IllegalArgumentException is thrown.
	 * 
	 * @param title the title to set
	 * @throws IllegalArgumentException if the title parameter is invalid.
	 */
	public void setTitle(String title) {
		if (title == null || title.isEmpty()) {
			throw new IllegalArgumentException("Invalid title.");
		}
		this.title = title;
	}

	/**
	 * Returns the meetingDays as a String unformatted
	 * @return meetingDays
	 */
	public String getMeetingDays() {
		return meetingDays;
	}

	/**
	 * Returns the meeting days and times of the activity, translated from military
	 * time to standard time format.
	 * 
	 * @return the meetingDays, startTime and endTime in a formatted string
	 */
	public String getMeetingString() {
		if (meetingDays.contains("A")) {
			return "Arranged";
		}
		return meetingDays + " " + getTimeString(startTime) + "-" + getTimeString(endTime);
	}

	/**
	 * Returns the standard time equivalent of the passed in military time. Adds a
	 * leadingZero to the minutes if it is less than 10. Changes the timeOfDay
	 * variable from "AM" to "PM" when hours is >= 12.
	 * 
	 * @param time (military)
	 * @return standardTime
	 */
	private String getTimeString(int time) {
		int hours = time / 100;
		String timeOfDay = "AM";
		if (hours > 12) {
			hours -= 12;
			timeOfDay = "PM";
		} else if (hours == 0) {
			hours = 12;
		} else if (hours == 12) {
			timeOfDay = "PM";
		}
		int minutes = time % 100;
		String leadingZero = "";
		if (minutes < 10) {
			leadingZero = "0";
		}
		return hours + ":" + leadingZero + minutes + timeOfDay;
	}

	/**
	 * Sets the meeting days and times of the activity. Checks for null or 
	 * empty parameters as well as out of bounds values; however, checks for
	 * specific input will be overwritten and handled in the inherited classes.
	 * 
	 * @param meetingDays the meetingDays to set
	 * @param startTime   the startTime to set
	 * @param endTime     the startTime to set
	 * @throws IllegalArgumentException if the parameters meetingDays, startTime, or
	 *                                  endTime are invalid.
	 */
	public void setMeetingDaysAndTime(String meetingDays, int startTime, int endTime) {
		if (meetingDays == null || meetingDays.isEmpty()) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}
		// Break apart startTime and endTime into hours and minutes
		int startTimeHours = startTime / 100;
		int startTimeMinutes = startTime % 100;
		int endTimeHours = endTime / 100;
		int endTimeMinutes = endTime % 100;
	
		// Check for out of bounds values
		if (startTimeHours < 0 || startTimeHours >= UPPER_HOUR) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}
		if (startTimeMinutes < 0 || startTimeMinutes >= UPPER_MINUTE) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}
		if (endTimeHours < 0 || endTimeHours >= UPPER_HOUR) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}
		if (endTimeMinutes < 0 || endTimeMinutes >= UPPER_MINUTE) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}
	
		// Check for overnight activities
		if (endTime < startTime) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}
	
		this.meetingDays = meetingDays;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	/**
	 * Returns the start time of the activity
	 * 
	 * @return the startTime
	 */
	public int getStartTime() {
		return startTime;
	}

	/**
	 * Returns the end time of the activity
	 * 
	 * @return the endTime
	 */
	public int getEndTime() {
		return endTime;
	}

	/**
	 * Overridden to reflect the changes made to .equals() -> utilizes all fields when checking for equality
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + endTime;
		result = prime * result + ((meetingDays == null) ? 0 : meetingDays.hashCode());
		result = prime * result + startTime;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}
	
	
	/**
	 * Overridden to check for equality among all fields between two Activities
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Activity other = (Activity) obj;
		if (endTime != other.endTime)
			return false;
		if (meetingDays == null) {
			if (other.meetingDays != null)
				return false;
		} else if (!meetingDays.equals(other.meetingDays))
			return false;
		if (startTime != other.startTime)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	/**
	 * Overridden method from the Conflict interface. Compares the current
	 * activity to the provided activity to compare the two and check for
	 * overlapping days and times. If there is at least one day with an
	 * intersecting minute (ie. Monday from 1:30PM-2:45PM and Monday from 2:45-3:30), 
	 * then the method throws a checked ConflictException.
	 * 
	 * @param possibleConflictingActivity the Activity to compare times against
	 * @throws ConflictException when there is at least one overlapping minute and day between the two activities.
	 */
	@Override
	public void checkConflict(Activity possibleConflictingActivity) throws ConflictException {
		
		if (!meetingDays.contains("A") && !possibleConflictingActivity.getMeetingDays().contains("A")) {
	
			for (int i = 0; i < meetingDays.length(); i++) {
				if (possibleConflictingActivity.getMeetingDays().contains(Character.toString(meetingDays.charAt(i)))) {
					if (startTime >= possibleConflictingActivity.getStartTime() && startTime <= possibleConflictingActivity.getEndTime()) {
						throw new ConflictException();
					}
					if (endTime >= possibleConflictingActivity.getStartTime() && endTime <= possibleConflictingActivity.getEndTime()) {
						throw new ConflictException();
					}
					if (possibleConflictingActivity.getStartTime() >= startTime && possibleConflictingActivity.getStartTime() <= endTime) {
						throw new ConflictException();
					}
					if (possibleConflictingActivity.getEndTime() >= startTime && possibleConflictingActivity.getEndTime() <= endTime) {
						throw new ConflictException();
					}
				}
			}
			
		}
		
	}
	
	

}