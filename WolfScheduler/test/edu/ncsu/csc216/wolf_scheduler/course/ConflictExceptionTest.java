/**
 * 
 */
package edu.ncsu.csc216.wolf_scheduler.course;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Class used to test ConflictException and its two
 * constructors.
 * 
 * @author michaelrichardson
 *
 */
class ConflictExceptionTest {

	/**
	 * Test method for the parameterized constructor of ConflictException
	 */
	@Test
	public void testConflictExceptionString() {
	    ConflictException ce = new ConflictException("Custom exception message");
	    assertEquals("Custom exception message", ce.getMessage());
	}

	/**
	 * Test method for the parameterless constructor of ConflictException
	 */
	@Test
	void testConflictException() {
	    ConflictException ce = new ConflictException();
	    assertEquals("Schedule conflict.", ce.getMessage());
	}

}
