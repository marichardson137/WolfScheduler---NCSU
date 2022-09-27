package edu.ncsu.csc216.wolf_scheduler.course;

/**
 * Class to describe a course and it's details including name, meeting days, instructor,
 * etc. Possesses getters and setters to manipulate the private data above.
 * Contains a specific toString() method for file interaction. Extends the Activity
 * class to inherit basic scheduling fields and methods.
 * 
 * @author Michael Richardson
 **/
public class Course extends Activity {

	/** Minimum name length. */
	private static final int MIN_NAME_LENGTH = 5;
	/** Maximum name length. */
	private static final int MAX_NAME_LENGTH = 8;
	/** Minimum letter count for course title. */
	private static final int MIN_LETTER_COUNT = 1;
	/** Maximum letter count for course title. */
	private static final int MAX_LETTER_COUNT = 4;
	/** Required digits for course title. */
	private static final int DIGIT_COUNT = 3;
	/** Required digits for section number */
	private static final int SECTION_LENGTH = 3;
	/** Maximum credit hours. */
	private static final int MAX_CREDITS = 5;
	/** Minimum credit hours. */
	private static final int MIN_CREDITS = 1;
	/** Course's name. */
	private String name;
	/** Course's section. */
	private String section;
	/** Course's credit hours */
	private int credits;
	/** Course's instructor */
	private String instructorId;
	/**
	 * Constructs a Course object with values for all fields.
	 * 
	 * @param name         name of Course
	 * @param title        title of Course
	 * @param section      section of Course
	 * @param credits      credit hours for Course
	 * @param instructorId instructor's unity id
	 * @param meetingDays  meeting days for Course as series of chars
	 * @param startTime    start time for Course
	 * @param endTime      end time for Course
	 */
	public Course(String name, String title, String section, int credits, String instructorId, String meetingDays,
			int startTime, int endTime) {
        super(title, meetingDays, startTime, endTime);
		setName(name);
		setSection(section);
		setCredits(credits);
		setInstructorId(instructorId);
	}

	/**
	 * Creates a Course with the given name, title, section, credits, instructorId,
	 * and meetingDays for courses that are arranged.
	 * 
	 * @param name         name of Course
	 * @param title        title of Course
	 * @param section      section of Course
	 * @param credits      credit hours for Course
	 * @param instructorId instructor's unity id
	 * @param meetingDays  meeting days for Course as series of chars
	 */
	public Course(String name, String title, String section, int credits, String instructorId, String meetingDays) {
		this(name, title, section, credits, instructorId, meetingDays, 0, 0);
	}

	/**
	 * Returns the name of the course
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the Course's name. If the name is null, has a length less than 5 or more
	 * than 8, does not contain a space between letter characters and number
	 * characters, has less than 1 or more than 4 letter characters, and not exactly
	 * three trailing digit characters, an IllegalArgumentException is thrown.
	 * 
	 * @param name the name to set
	 * @throws IllegalArgumentException if the name parameter is invalid
	 */
	private void setName(String name) {
		// Throw exception if the name is null
		if (name == null) {
			throw new IllegalArgumentException("Invalid course name.");
		}

		// Throw exception if the name is an empty string
		// Throw exception if the name contains less than 5 character or greater than 8
		// characters
		if (name.length() < MIN_NAME_LENGTH || name.length() > MAX_NAME_LENGTH) {
			throw new IllegalArgumentException("Invalid course name.");
		}

		// Check for pattern of L[LLL] NNN
		int numLetters = 0;
		int numDigits = 0;
		boolean foundSpace = false;
		for (int i = 0; i < name.length(); i++) {
			if (!foundSpace) {
				if (Character.isLetter(name.charAt(i))) {
					numLetters++;
				} else if (name.charAt(i) == ' ') {
					foundSpace = true;
				} else {
					throw new IllegalArgumentException("Invalid course name.");
				}
			} else if (foundSpace) {
				if (Character.isDigit(name.charAt(i))) {
					numDigits++;
				} else {
					throw new IllegalArgumentException("Invalid course name.");
				}
			}
		}
		// Check that the number of letters is correct
		if (numLetters < MIN_LETTER_COUNT || numLetters > MAX_LETTER_COUNT) {
			throw new IllegalArgumentException("Invalid course name.");
		}
		// Check that the number of digits is correct
		if (numDigits != DIGIT_COUNT) {
			throw new IllegalArgumentException("Invalid course name.");
		}
		this.name = name;
	}

	/**
	 * Returns the section of the course
	 * @return the section
	 */
	public String getSection() {
		return section;
	}

	/**
	 * Sets the section of the course. If the section not exactly three digits an
	 * IllegalArgumentException is thrown.
	 * 
	 * @param section the section to set
	 * @throws IllegalArgumentException if the section parameter is invalid.
	 */
	public void setSection(String section) {
		if (section == null || section.length() != SECTION_LENGTH) {
			throw new IllegalArgumentException("Invalid section.");
		}
		if (!Character.isDigit(section.charAt(0)) || !Character.isDigit(section.charAt(1))
				|| !Character.isDigit(section.charAt(2))) {
			throw new IllegalArgumentException("Invalid section.");
		}
		this.section = section;
	}

	/**
	 * Returns the number of credit hours for the course
	 * @return the credits
	 */
	public int getCredits() {
		return credits;
	}

	/**
	 * Sets the number of credit hours for the course. If credits is not a number
	 * (NaN) or less than 1 or greater than 5, an IllegalArgumentException is
	 * thrown.
	 * 
	 * @param credits the credits to set
	 * @throws IllegalArgumentException if the credits parameter is invalid.
	 */
	public void setCredits(int credits) {
		if (credits < MIN_CREDITS || credits > MAX_CREDITS) {
			throw new IllegalArgumentException("Invalid credits.");
		}
		this.credits = credits;
	}

	/**
	 * Returns the instructor's ID for the course
	 * 
	 * @return the instructorId
	 */
	public String getInstructorId() {
		return instructorId;
	}

	/**
	 * Sets the instructor's ID for the course. If instructorId is null or an empty
	 * string, an IllegalArgumentException is thrown.
	 * 
	 * @param instructorId the instructorId to set
	 * @throws IllegalArgumentException if the instructorId parameter is invalid.
	 */
	public void setInstructorId(String instructorId) {
		if (instructorId == null || instructorId.isEmpty()) {
			throw new IllegalArgumentException("Invalid instructor id.");
		}
		this.instructorId = instructorId;
	}

	/**
	 * Returns a comma separated value String of all Course fields. Output is
	 * displayed differently for file interactions
	 * 
	 * @return String representation of Course
	 */
	@Override
	public String toString() {
		if ("A".equals(getMeetingDays())) {
			return name + "," + getTitle() + "," + section + "," + credits + "," + instructorId + "," + getMeetingDays();
		}
		return name + "," + getTitle() + "," + section + "," + credits + "," + instructorId + "," + getMeetingDays() + ","
				+ getStartTime() + "," + getEndTime();
	}

	/**
	 * Overridden to reflect the changes made to .equals() -> utilizes all fields when checking for equality
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + credits;
		result = prime * result + ((instructorId == null) ? 0 : instructorId.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((section == null) ? 0 : section.hashCode());
		return result;
	}

	/**
	 * Overridden to check for equality among all fields between two Courses
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Course other = (Course) obj;
		if (credits != other.credits)
			return false;
		if (instructorId == null) {
			if (other.instructorId != null)
				return false;
		} else if (!instructorId.equals(other.instructorId))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (section == null) {
			if (other.section != null)
				return false;
		} else if (!section.equals(other.section))
			return false;
		return true;
	}

	/**
	 * Overwritten abstract method from Activity used to display a short string
	 * array when populating the course catalog and student schedule blocks
	 * of the GUI. Returns only four fields of a Course: the name, section, title,
	 * and meeting days + times.
	 * @return shortDisplay string array with four fields
	 */
	@Override
	public String[] getShortDisplayArray() {
		String[] shortDisplay = {name, section, getTitle(), getMeetingString()};
		return shortDisplay;
	}

	/**
	 * Overwritten abstract method from Activity used to display a long string
	 * array when printing all information for a Course in the final schedule 
	 * panel of the GUI. Returns all fields of a Course except the start and 
	 * end time: the name, section, title, credits, instructorId, meetingDays, 
	 * and an empty string (for a field that Event has, but Course does not)
	 * @return longDisplay string array with four fields
	 */
	@Override
	public String[] getLongDisplayArray() {
		String[] longDisplay = {name, section, getTitle(), String.valueOf(credits), instructorId, getMeetingString(), ""};
		return longDisplay;
	}

	
	/** 
	 * Overwritten from the Activity class so that Event and Course
	 * can have different implementations of setMeetingDaysAndTime() while
	 * retaining common checks in the Activity class. If the meeting days consist of
	 * any characters other than 'M', ‘T’, ‘W’, ‘H’, ‘F’, 'A', or meeting days has
	 * a duplicate character, or meeting days has additional characters with 'A', or
	 * the start time is not between 0000 and 2359, or the end time is not between
	 * 0000 and 2359, or the end time is less than the start time, or a start and
	 * end time is listed when meeting days is 'A', then an IllegalArgumentException
	 * is thrown.
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
			if ("A".equals(meetingDays)) { // Arranged
				if (startTime != 0 || endTime != 0) {
					throw new IllegalArgumentException("Invalid meeting days and times.");
				}
				super.setMeetingDaysAndTime(meetingDays, 0, 0);
			} else { // Not Arranged
				// Count frequencies
				int[] weekdayLetterCounts = new int[5];
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
			}
		} catch (NullPointerException e) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}
		super.setMeetingDaysAndTime(meetingDays, startTime, endTime);
	}

	/**
	 * Overridden abstract method from the Activity class
	 * to compare two Courses and return if they are duplicates
	 * or not based on their name equality
	 * @param activity the activity you are checking for duplication
	 * @return isDuplicate boolean
	 */
	@Override
	public boolean isDuplicate(Activity activity) {
		/* Check to see if the provided activity is an instance of
		 * Course to prevent a ClassCastException */
		if (activity instanceof Course) {
			// Cast the activity to a Course
			Course course = (Course) activity;
			// Check for equality among the name field
			return course.getName().equals(this.name);
		}
		return false;
	}
	
	
	
	

}
