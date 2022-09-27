package edu.ncsu.csc216.wolf_scheduler.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

import edu.ncsu.csc216.wolf_scheduler.course.Course;

/**
 * Reads Course records from text files.  Writes a set of CourseRecords to a file.
 * 
 * @author Sarah Heckman
 * @author Michael Richardson
 */
public class CourseRecordIO {

    /**
     * Reads course records from a file and generates a list of valid Courses.  Any invalid
     * Courses are ignored.  If the file to read cannot be found or the permissions are incorrect
     * a File NotFoundException is thrown.
     * @param fileName file to read Course records from
     * @return a list of valid Courses
     * @throws FileNotFoundException if the file cannot be found or read
     */
    public static ArrayList<Course> readCourseRecords(String fileName) throws FileNotFoundException {
    	Scanner fileReader = new Scanner(new FileInputStream(fileName));  //Create a file scanner to read the file
        ArrayList<Course> courses = new ArrayList<Course>(); //Create an empty array of Course objects
        while (fileReader.hasNextLine()) { //While we have more lines in the file 
            try { //Attempt to do the following
                //Read the line, process it in readCourse, and get the object
                //If trying to construct a Course in readCourse() results in an exception, flow of control will transfer to the catch block, below
                Course course = readCourse(fileReader.nextLine()); 

                //Create a flag to see if the newly created Course is a duplicate of something already in the list  
                boolean duplicate = false;
                //Look at all the courses in our list
                for (int i = 0; i < courses.size(); i++) {
                    //Get the course at index i
                    Course current = courses.get(i);
                    //Check if the name and section are the same
                    if (course.getName().equals(current.getName()) &&
                            course.getSection().equals(current.getSection())) {
                        //It's a duplicate!
                        duplicate = true;
                        break; //We can break out of the loop, no need to continue searching
                    }
                }
                //If the course is NOT a duplicate
                if (!duplicate) {
                    courses.add(course); //Add to the ArrayList!
                } //Otherwise ignore
            } catch (IllegalArgumentException e) {
                //The line is invalid b/c we couldn't create a course, skip it!
            }
        }
        //Close the Scanner b/c we're responsible with our file handles
        fileReader.close();
        //Return the ArrayList with all the courses we read!
        return courses;
    }
    
	/**
	 * Static delegate method used to convert the CSV line of each course in the file to
	 * a new Course object and return it to the readCourseRecords() method
	 * @param line consisting of a single CSV line of course data
	 * @return course representing the course created from the provided line
	 * @throws IllegalArgumentException if the course cannot be created from the given data
	 */
    private static Course readCourse(String line) throws IllegalArgumentException {
    	// Initialize a Scanner object to parse the course line passed in 
    	Scanner lineReader = new Scanner(line);
    	lineReader.useDelimiter(","); // Set the delimiter to use commas as opposed to white space
    	
    	// Attempt to parse the line and create a new Course object
    	try {
    		// Gather the base fields of Course
    		String name = lineReader.next();
    		String title = lineReader.next();
    		String section = lineReader.next();
    		int creditHours = lineReader.nextInt();
    		String instructor = lineReader.next();
    		String meetingDays = lineReader.next();
    		
    		// Check to see if the course is asynchronous
    		if ("A".equals(meetingDays)) {
    			if (lineReader.hasNext()) { // If the course is asynchronous and has a meeting time, throw an IAE
    	    		lineReader.close(); // Prevent memory leaks
    				throw new IllegalArgumentException();
    			} // Otherwise, return the new Course object using the fields from above
        		lineReader.close(); // Prevent memory leaks
    			return new Course(name, title, section, creditHours, instructor, meetingDays);
    		}

    		// The course is not asynchronous, so gather the start and end time fields
    		int startTime = lineReader.nextInt();
    		int endTime = lineReader.nextInt();
    		
    		// If there are additional fields in the line, throw an IAE
    		if (lineReader.hasNext()) {
        		lineReader.close(); // Prevent memory leaks
				throw new IllegalArgumentException();
			} // Otherwise, return the new Course object using the fields from above
    		lineReader.close(); // Prevent memory leaks
    		return new Course(name, title, section, creditHours, instructor, meetingDays, startTime, endTime); 
    	
    	// If there is an error with parsing the line, throw an IAE so readCourseRecords() can handle the error
    	} catch (NoSuchElementException e) { // Catches InputMismatchException as well 
    		lineReader.close(); // Prevent memory leaks
    		throw new IllegalArgumentException(); // Pass the exception onto the caller readCourseRecords() method
    	}
    }

}
