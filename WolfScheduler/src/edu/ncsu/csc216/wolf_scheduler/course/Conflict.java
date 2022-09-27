/**
 * 
 */
package edu.ncsu.csc216.wolf_scheduler.course;

/**
 * Interface used to evaluate conflicts between two
 * activities. The method checkConflict(Activity) will
 * take into account the dates and times of the current
 * activity and the passed in activity to compare the two
 * and determine if their is a conflict. All methods are 
 * assumed to be public abstract methods that Activity
 * will implement.
 * 
 * @author Michael Richardson
 *
 */
public interface Conflict {

	/**
	 * Used to compare the dates and times of the current
	 * activity and a second, provided activity, to determine
	 * if their is a conflict and throw an exception. 
	 * 
	 * @param possibleConflictingActivity Activity to compare
	 * @throws ConflictException When the two activities overlap
	 */
	void checkConflict(Activity possibleConflictingActivity) throws ConflictException;

}
