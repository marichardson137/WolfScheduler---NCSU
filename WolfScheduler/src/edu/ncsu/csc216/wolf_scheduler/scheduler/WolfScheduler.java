/**
 * 
 */
package edu.ncsu.csc216.wolf_scheduler.scheduler;

import java.io.FileNotFoundException;

import java.io.IOException;
import java.util.ArrayList;

import edu.ncsu.csc216.wolf_scheduler.course.Activity;
import edu.ncsu.csc216.wolf_scheduler.course.ConflictException;
import edu.ncsu.csc216.wolf_scheduler.course.Course;
import edu.ncsu.csc216.wolf_scheduler.course.Event;
import edu.ncsu.csc216.wolf_scheduler.io.ActivityRecordIO;
import edu.ncsu.csc216.wolf_scheduler.io.CourseRecordIO;

/** Class used to organize a student's schedule. Client has the ability to
 * access the course catalog (all courses), as well as add and remove a course from
 * their schedule, rename the schedule, and retrieve their current schedule.
 * The constructor takes in a fileName of the course catalog to use for scheduling.
 * 
 * @author michaelrichardson
 */
public class WolfScheduler {
	
	/** List used to store all available courses */
	private ArrayList<Course> catalog;
	/** List used to store the student's current scheduled courses */
	private ArrayList<Activity> schedule;
	/** Title of the current schedule */
	private String title;

	/**
	 * Constructs a WolfScheduler object by accepting the fileName of a course catalog.
	 * Initializes an empty list to store the student's schedule and sets the title to
	 * a default value of "My Schedule".
	 * @param fileName of the course catalog .txt file
	 * @throws IllegalArgumentException if the file is not found
	 */
	public WolfScheduler(String fileName) throws IllegalArgumentException {
		schedule = new ArrayList<Activity>(); 
		title = "My Schedule";
		
		// Attempt to load the course catalog from the file
		try {
			catalog = CourseRecordIO.readCourseRecords(fileName);
		} catch (FileNotFoundException e) {
			// Propagate the IAE to the WolfSchedulerGUI class if the file is not found
			throw new IllegalArgumentException("Cannot find file.");
		}	
	}

	/**
	 * Formats the catalog list of courses into a table with one row
	 * for each course and four columns for name, section, title, and meeting times.
	 * @return courseTable 2D array
	 */
	public String[][] getCourseCatalog() {
		// Initialize  a new 2D array with the same number of rows as courses and 4 columns
        String [][] catalogArray = new String[catalog.size()][4];
		// Iterate through all courses in the catalog and add their name, section, title, and meeting times to the table
		for (int i = 0; i < catalog.size(); i++) {
	          Course c = catalog.get(i);
	          catalogArray[i] = c.getShortDisplayArray();
		}
		// Return the completed table
		return catalogArray; 
	}

	/**
	 * Formats the schedule list of activities into a table with one row
	 * for each activity and four columns for name, section, title, and meeting times.
	 * @return scheduleTable 2D array
	 */
	public String[][] getScheduledActivities() {
		// Initialize a new 2D array with the same number of rows as activities and 4 columns
		String[][] scheduleArray = new String[schedule.size()][4]; 
		// Iterate through all activities in the schedule and add their name, section, title, and meeting times.
		for (int i = 0; i < schedule.size(); i++) {
	          Activity a = schedule.get(i);
	          scheduleArray[i] = a.getShortDisplayArray();
		}
		// Return the completed table
		return scheduleArray; 
	}

	/**
	 * Formats the schedule list of activities into a table with one row
	 * for each activity and seven columns for name, section, title,
	 * credits, instructorId, meetingDays, and event details.
	 * @return scheduleTable 2D array
	 */
	public String[][] getFullScheduledActivities() {
		// Initialize  a new 2D array with the same number of rows as courses and 7 columns
		String[][] scheduleArray = new String[schedule.size()][7]; 
		// Iterate through all courses in the schedule and add their name, section, title, credits, instructorId, meetingDays, and event details to the table
		for (int i = 0; i < schedule.size(); i++) {
			Activity a = schedule.get(i);
			scheduleArray[i] = a.getLongDisplayArray();
		}
		// Return the completed table
		return scheduleArray; 
	}

	/**
	 * Method used to find and retrieve a specific course and section from
	 * the course catalog list. Returns the course if found, returns null
	 * if the course is not found in the catalog.
	 * @param name of the desired course
	 * @param section of the desired course
	 * @return currentCourse if found in the catalog
	 */
	public Course getCourseFromCatalog(String name, String section) {
		// Iterate through the course catalog
		for (int i = 0; i < catalog.size(); i++) {
			Course currentCourse = catalog.get(i);
			// Check to see if the provided name and section match the current Course's name and section
			if (currentCourse.getName().equals(name) && 
				currentCourse.getSection().equals(section)) {
				// Return the current Course and exit the loop if a match is found 				
				return currentCourse;  
			}
		}
		// Return null if the course is not found in the catalog
		return null;
	}

	/**
	 * Method used to add a course from the catalog to the student's schedule.
	 * If the course is not found in the catalog, false is returned. If the 
	 * course (name) already exists in the student's schedule, an IAE is thrown.
	 * Otherwise, the course is added to the student's schedule and the value 
	 * of true is returned denoting a successful addition.
	 * @param name of the course to add
	 * @param section of the course to add
	 * @return true if successful and false otherwise
	 * @throws IllegalArgumentException if the course is already in the student's schedule
	 */
	public boolean addCourseToSchedule(String name, String section) throws IllegalArgumentException {
		// Retrieve the desired course from the catalog
		Course course = getCourseFromCatalog(name, section);
		// Return false if the course does not exist in the catalog
		if (course == null) {
			return false;
		}
		
		// Loop through the student's current schedule to search for the existing course
		for (int i = 0; i < schedule.size(); i++) {
			Activity scheduledCourse = schedule.get(i);
			// Check for equality among the name only because a student cannot take multiple sections of the same class
			if (scheduledCourse.isDuplicate(course)) {
				// Throw an IAE if the course is already found in the student's schedule	
				throw new IllegalArgumentException("You are already enrolled in " + name);
			}
			
			// Check for conflicts
			try {
				course.checkConflict(scheduledCourse);
			} catch (ConflictException e) {
				throw new IllegalArgumentException("The course cannot be added due to a conflict.");
			}
		}
		

		
		/* No issues were found with the desired course so it 
		 * is added to the student's schedule and true is returned */
		schedule.add(course);
		return true;
	}
	
	/**
	 * Method used to add an event to the student's schedule. 
	 * If the event (title) is found to be a duplicate, an IAE is thrown.
	 * Otherwise, the event is added to the student's schedule list.
	 * @param eventTitle		the event's title
	 * @param eventMeetingDays	the event's meeting days
	 * @param eventStartTime	the event's start time
	 * @param eventEndTime		the event's end time
	 * @param eventDetails		the event's details
	 * @throws IllegalArgumentException if the event is determined to be a duplicate or conflicts with another Activity
	 */
	public void addEventToSchedule(String eventTitle, String eventMeetingDays, int eventStartTime, int eventEndTime, String eventDetails) {
		Event event = new Event(eventTitle, eventMeetingDays, eventStartTime, eventEndTime, eventDetails);
		// Loop through the student's current schedule to search for the existing event
		for (int i = 0; i < schedule.size(); i++) {
			Activity scheduledEvent = schedule.get(i);
			// Check for equality among the title only because a student cannot have multiple events with the same title
			if (scheduledEvent.isDuplicate(event)) {
				// Throw an IAE if the event is already found in the student's schedule	
				throw new IllegalArgumentException("You have already created an event called " + eventTitle);
			}
			
			// Check for conflicts
			try {
				event.checkConflict(scheduledEvent);
			} catch (ConflictException e) {
				throw new IllegalArgumentException("The event cannot be added due to a conflict.");
			}
		}
		/* No issues were found with the desired event so it 
		 * is added to the student's schedule */
		schedule.add(event);
	}
	
	
	/**
	 * Method used to remove an activity from the student's schedule. If the
	 * index is a valid index in the range of the schedule size, the method will
	 * remove it and return true. Otherwise, the method will return false and 
	 * the schedule will remain unchanged.
	 * @param idx index of the Activity to remove
	 * @return true if successful and false otherwise
	 */
	public boolean removeActivityFromSchedule(int idx) {
		try {
			schedule.remove(idx);
			return true;
		} catch (IndexOutOfBoundsException e) {
			// The course was not found in the student's schedule and false is returned
			return false;
		}
	}
	
	/**
	 * Creates a new empty list to assign schedule to. Essentially clears 
	 * the schedule and removes all classes from it.
	 */
	public void resetSchedule() {
		schedule = new ArrayList<Activity>();
		
	}

	/**
	 * Returns the current schedule title
	 * @return title of the schedule
	 */
	public String getScheduleTitle() {
		return title;
	}

	/**
	 * Renames the title of the schedule
	 * @param title the title to set
	 * @throws IllegalArgumentException if the title is null
	 */
	public void setScheduleTitle(String title) throws IllegalArgumentException {
		if (title == null) {
			throw new IllegalArgumentException("Title cannot be null.");
		}
		this.title = title;
	}

	/**
	 * Method used to export the current schedule to a file provided as a parameter.
	 * Uses the CourseRecordIO class to abstract the file writing.
	 * @param fileName of the .txt file 
	 * @throws IllegalArgumentException if the file cannot be found or cannot be written to.
	 */
	public void exportSchedule(String fileName) throws IllegalArgumentException {
		// Attempt to write to the file
		try {
			ActivityRecordIO.writeActivityRecords(fileName, schedule);
		} catch (IOException e) {
			// Throw a new IAE if the file cannot be found or cannot be written to
			throw new IllegalArgumentException("The file cannot be saved.");
		}
	}



}
