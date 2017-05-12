package starwars.entities;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Capability;
import starwars.SWEntity;
import starwars.actions.Fill;
import starwars.actions.Drink;
import starwars.actions.Empty;

/**
 * A canteen that can be used to contain water.
 * 
 * It can be filled at a Reservoir, or any other Entity
 * that has a Dip affordance.
 * 
 * Canteen can be drank from once it is full
 * @author Daryl Ho - change made for Assignment 2
 * 
 * @author Robert Merkel
 * 
 */
public class Canteen extends SWEntity implements Fillable, Emptiable{

	private int capacity;
	private int level;
	private float depletionRate;
	
	/**
	 * Constructor the <code>Canteen</code> class. This constructor will
	 * <ul>
	 *  <li> set descriptions for the <code>Canteen</code> </li>
	 *  <li> set the capacity of the <code>Canteen</code></li>
	 *  <li> set the level of the <code>Canteen</code></li>
	 *  <li> add the <code>Fill</code> affordance to the <code>Canteen</code></li>
	 *  <li> add the <code>Empty</code> affordance to the <code>Canteen</code></li>
	 *  <li> add the <code>Drink</code> affordance to the <code>Canteen</code></li>
	 *  <li> add the FILLABLE <code>Capability</code> to the <code>Canteen</code></li>
	 *  <li> set the depletionRate of the <code>Canteen</code> </li>
	 * </ul>
	 *  
	 * @author Daryl Ho
	 * @param m	<code>MessageRendere</code> being used
	 * @param capacity maximum level of liquid stored by canteen
	 * @param initialLevel initial level of liquid held by canteen
	 */
	public Canteen(MessageRenderer m, int capacity, int initialLevel)  {
		super(m);
		this.shortDescription = "a canteen";
		this.longDescription = "a slightly battered aluminium canteen";

		this.capacity = capacity;
		this.level= initialLevel;
		capabilities.add(Capability.FILLABLE);
		this.addAffordance(new Fill(this, m));
		this.addAffordance(new Empty(this, m));
		this.addAffordance(new Drink(this, m));
		
		// New variable to determine how fast this entity is emptied
		// (How much % the level depletes per emptying attempt)
		// Using % allows for different capacity, and hence healing power items to be added
	    depletionRate = (float) 0.5;
	    
	    //If initial level isn't empty, we can drink from it
	    if (initialLevel != 0) {
	    	capabilities.add(Capability.EMPTIABLE);
	    }
	    
	}
	/* (non-Javadoc)
	 * @see starwars.entities.Fillable#fill()
	 */
	public void fill() {
		level = capacity;
		capabilities.add(Capability.EMPTIABLE);
		capabilities.remove(Capability.FILLABLE);
	}
	
	/* (non-Javadoc)
	 * @see starwars.entities.Emptiable#empty()
	 */
	public void empty() {
		int emptyAmount = getEmptiedAmount();
		level -= emptyAmount;
		
		// Only add FILLABLE capability if depleted from full Canteen
		if (!capabilities.contains(Capability.FILLABLE)) {
			capabilities.add(Capability.FILLABLE);
		}
		// Remove EMPTIABLE once Canteen is empty!
		if (isEmpty()) {
			capabilities.remove(Capability.EMPTIABLE);
		}
	}
	/*
	 * Method added to get amount emptied at each emptying attempt
	 * Because drinking from canteen heals by how much you empty it
	 * Note that items can start at a lower level than depeletionRate, in that case
	 * You simply deplete by how much is left
	 * @author Daryl Ho
	 */
	public int getEmptiedAmount() {
		int emptyAmount = (int) Math.floor(depletionRate * capacity);
		if (level - emptyAmount < 0) {
			emptyAmount = level;
			}
		return emptyAmount;
	}
	
	/*
	 * Method added to check if the canteen is empty or not
	 */
	public boolean isEmpty() {
		if (level == 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	@Override 
	public String getShortDescription() {
		return shortDescription + " [" + level + "/" + capacity + "]";
	}
	
	@Override
	public String getLongDescription () {
		return longDescription + " [" + level + "/" + capacity + "]";
	}
}
