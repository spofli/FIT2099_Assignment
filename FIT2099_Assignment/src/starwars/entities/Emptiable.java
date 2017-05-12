package starwars.entities;

/**
 * Interface for SWEntities that are emptiable
 * 
 * All <code>Emptiable</code> objects must have <code>Capability</code> EMPTIABLE
 * @author Seolhyun95
 */
public interface Emptiable {
	/**
	 * Empty this SWEntity
	 */
	void empty();
	
	/**
	 * Get value which is emptied
	 * Used in healing calculations
	 */
	int getEmptiedAmount();
	
	/**
	 * Get True/False if item is empty or not
	 */
	boolean isEmpty();
}
