/**
 * 
 */
package edu.ncsu.csc216.wolf_scheduler.course;

/**
 * Class to describe an event and it's details including title, meeting days,
 * start and end time, etc. Possesses getters and setters to manipulate the private data above.
 * Contains a specific toString() method for file interaction. Extends the Activity
 * class to inherit basic scheduling fields and methods.
 * @author michaelrichardson
 *
 */
public class Event extends Activity {
	
	/** String of the event details */
	private String eventDetails;

	/**
	 * Constructs an Event object with values for all fields
	 * @param title			title of the event
	 * @param meetingDays	meeting days of the event
	 * @param startTime		start time of the event
	 * @param endTime		end time of the event
	 * @param eventDetails	event details of the event
	 */
	public Event(String title, String meetingDays, int startTime, int endTime, String eventDetails) {
		super(title, meetingDays, startTime, endTime);
		setEventDetails(eventDetails);
	}

	/**
	 * Overwritten abstract method from Activity used to display a short string
	 * array when populating the student schedule block of the GUI. Returns only 
	 * four fields of a Event: empty string, empty string, title, and meeting days + times.
	 * The empty string fields are there since Courses have more data to display in a table.
	 * @return shortDisplay string array with four fields
	 */
	@Override
	public String[] getShortDisplayArray() {
		String[] shortDisplay = {"", "", getTitle(), getMeetingString()};
		return shortDisplay;
	}

	/**
	 * Overwritten abstract method from Activity used to display a long string
	 * array when printing all information for an Event in the final schedule 
	 * panel of the GUI. Returns all fields of an Event except the start and 
	 * end time: empty string, empty string, title, empty string, empty string, 
	 * meeting days + times, and event details. The empty string fields are there 
	 * since Courses have more data to display in a table.
	 * @return longDisplay string array with four fields
	 */
	@Override
	public String[] getLongDisplayArray() {
		String[] longDisplay = {"", "", getTitle(), "", "", getMeetingString(), eventDetails};
		return longDisplay;
	}

	/**
	 * Returns the eventDetails string
	 * @return the eventDetails
	 */
	public String getEventDetails() {
		return eventDetails;
	}

	/**
	 * Sets the event detail string to the provided parameter.
	 * Throws an IAE if the parameter is invalid.
	 * @param eventDetails the eventDetails to set
	 * @throws IllegalArgumentException if eventDetails is null
	 */
	public void setEventDetails(String eventDetails) {
		if (eventDetails == null) {
			throw new IllegalArgumentException("Invalid event details.");
		}
		this.eventDetails = eventDetails;
	}

	/**
	 * Returns a comma separated value String of all Event fields. Output is
	 * displayed differently for file interactions
	 * 
	 * @return String representation of Event
	 */
	@Override
	public String toString() {
		return getTitle() + "," + getMeetingDays() + "," + getStartTime() + "," + getEndTime() + "," + eventDetails;
	}
	
	/** 
	 * Overwritten from the Activity class so that Event and Course
	 * can have different implementations of setMeetingDaysAndTime() while
	 * retaining common checks in the Activity class. If the meeting days consist of
	 * any characters other than 'M', ‘T’, ‘W’, ‘H’, ‘F’, 'S', 'U', or meeting days has
	 * a duplicate character, or the start time is not between 0000 and 2359, or the end 
	 * time is not between 0000 and 2359, or the end time is less than the start time,
	 * then an IllegalArgumentException is thrown.
	 * 
	 * @param meetingDays the meetingDays to set
	 * @param startTime   the startTime to set
	 * @param endTime     the startTime to set
	 * @throws IllegalArgumentException if the parameters meetingDays, startTime, or
	 *                                  endTime are invalid.
	 */
	@Override
	public void setMeetingDaysAndTime(String meetingDays, int startTime, int endTime) {
		try {
			// Count frequencies
			int[] weekdayLetterCounts = new int[7];
			for (int i = 0; i < meetingDays.length(); i++) { 
				switch (meetingDays.charAt(i)) {
				case 'M':
					weekdayLetterCounts[0]++;
					break;
				case 'T':
					weekdayLetterCounts[1]++;
					break;
				case 'W':
					weekdayLetterCounts[2]++;
					break;
				case 'H':
					weekdayLetterCounts[3]++;
					break;
				case 'F':
					weekdayLetterCounts[4]++;
					break;
				case 'S':
					weekdayLetterCounts[5]++;
					break;
				case 'U':
					weekdayLetterCounts[6]++;
					break;
				default:
					throw new IllegalArgumentException("Invalid meeting days and times.");
				}
			}
			// Check for duplicates
			for (int i = 0; i < weekdayLetterCounts.length; i++) { 
				if (weekdayLetterCounts[i] > 1) {
					throw new IllegalArgumentException("Invalid meeting days and times.");
				}
			}
		} catch (NullPointerException e) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}
		super.setMeetingDaysAndTime(meetingDays, startTime, endTime);
	}
	
	/**
	 * Overridden abstract method from the Activity class
	 * to compare two Events and return if they are duplicates
	 * or not based on their title equality
	 * @param activity the activity you are checking for duplication
	 * @return isDuplicate boolean
	 */
	@Override
	public boolean isDuplicate(Activity activity) {
		/* Check to see if the provided activity is an instance of
		 * Event to prevent a ClassCastException */
		if (activity instanceof Event) {
			// Cast the activity to a Event
			Event event = (Event) activity;
			// Check for equality among the title field
			return event.getTitle().equals(this.getTitle());
		}
		return false;
	}
	

}
