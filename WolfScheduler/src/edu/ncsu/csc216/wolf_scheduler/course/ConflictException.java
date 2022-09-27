/**
 * 
 */
package edu.ncsu.csc216.wolf_scheduler.course;

/**
 * Checked exception that is used to notify the 
 * client of a conflict between the times / dates
 * of two Activities. 
 * 
 * @author Michael Richardson
 *
 */
public class ConflictException extends Exception {

	/** ID used for serialization. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor of ConflictException that takes in 
	 * a message used for informing the client.
	 * 
	 * @param message the message to send to the client.
	 */
	public ConflictException(String message) {
		super(message);
	}
	
	/**
	 * Parameterless constructor of a ConflictException
	 * that provides the message "Schedule conflict."
	 */
	public ConflictException() {
		super("Schedule conflict.");
	}

}
