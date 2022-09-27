/**
 * 
 */
package edu.ncsu.csc216.wolf_scheduler.course;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Class used to test the conflict checking functionality
 * of the Activity class. All other methods in activity are
 * tested through the inherited classes
 * 
 * @author Michael Richardson
 *
 */
class ActivityTest {

	/**
	 * Test method for checkConflict in the Activity class when there is no conflict
	 */
	@Test
	void testCheckConflict() {
		
		Activity a1 = new Course("CSC 216", "Software Development Fundamentals", "001", 3, "sesmith5", "MW", 1330, 1445);
	    Activity a2 = new Course("CSC 216", "Software Development Fundamentals", "001", 3, "sesmith5", "TH", 1330, 1445);
	    
	    assertDoesNotThrow(() -> a1.checkConflict(a2));
	    assertDoesNotThrow(() -> a2.checkConflict(a1));
	    
		Activity a3 = new Course("CSC 216", "Software Development Fundamentals", "001", 3, "sesmith5", "MW", 1330, 1445);
	    Activity a4 = new Course("CSC 216", "Software Development Fundamentals", "001", 3, "sesmith5", "MW", 1446, 1700);
	    
	    assertDoesNotThrow(() -> a3.checkConflict(a4));
	    assertDoesNotThrow(() -> a4.checkConflict(a3));
	    
	}
	
	/**
	 * Test method for checkConflict in the Activity class when there is a conflict
	 */
	@Test
	public void testCheckConflictWithConflict() {
		
	    Activity a1 = new Course("CSC 216", "Software Development Fundamentals", "001", 3, "sesmith5", "MW", 1330, 1445);
	    Activity a2 = new Course("CSC 216", "Software Development Fundamentals", "001", 3, "sesmith5", "M", 1330, 1445);
		
	    Exception e1 = assertThrows(ConflictException.class, () -> a1.checkConflict(a2));
	    assertEquals("Schedule conflict.", e1.getMessage());
		
	    Exception e2 = assertThrows(ConflictException.class, () -> a2.checkConflict(a1));
	    assertEquals("Schedule conflict.", e2.getMessage());
	    
	    Activity a3 = new Course("CSC 216", "Software Development Fundamentals", "001", 3, "sesmith5", "THF", 1330, 1445);
	    Activity a4 = new Course("CSC 216", "Software Development Fundamentals", "001", 3, "sesmith5", "MTWHF", 1445, 1700);
		
 	    Exception e3 = assertThrows(ConflictException.class, () -> a3.checkConflict(a4));
	    assertEquals("Schedule conflict.", e3.getMessage());
		
	    Exception e4 = assertThrows(ConflictException.class, () -> a4.checkConflict(a3));
	    assertEquals("Schedule conflict.", e4.getMessage());
	    
	}

}
