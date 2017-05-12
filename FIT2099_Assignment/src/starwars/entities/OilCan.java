package starwars.entities;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Capability;
import starwars.SWEntity;
import starwars.actions.Empty;

/**
 * An OilCan stores oil. 
 * 
 * Owner can use it to oil nearby <code>Droid</code>
 * Unlike the <code>Canteen</code>, has no way to be refilled
 * 
 * @author Daryl Ho 
 * 
 */
public class OilCan extends SWEntity implements Emptiable{

	private int capacity;
	private int level;
	private float depletionRate;
	
	/**
	 * Constructor the <code>OilCan</code> class. This constructor will
	 * <ul>
	 *  <li> set descriptions for the <code>OilCan</code> </li>
	 *  <li> set the capacity of the <code>OilCan</code></li>
	 *  <li> set the level of the <code>OilCan</code></li>
	 *  <li> add the <code>Drink</code> affordance to the <code>OilCan</code></li>
	 *  <li> add the FILLABLE <code>Capability</code> to the <code>OilCan</code></li>
	 *  <li> add the OILABLE <code>Capability</code> to the <code>OilCan</code></li>
	 *  <li> set the depletionRate of the <code>OilCan</code> </li>
	 * </ul>
	 * 
	 * @param m the <code>MessageRenderer</code> used
	 * @param capacity maximum level of liquid stored by <code>OilCan</code>
	 * @param initialLevel initial level of liquid held by <code>OilCan</code>
	 */
	public OilCan(MessageRenderer m, int capacity, int initialLevel)  {
		super(m);
		this.shortDescription = "an oil can";
		this.longDescription = "a rusty oil can with a long spout";

		this.capacity = capacity;
		this.level= initialLevel;
		this.addAffordance(new Empty(this, m));
		capabilities.add(Capability.OILABLE);
		
		// Determines how fast this entity is emptied
		// (How much % the level depletes per emptying attempt)
		// Using % allows for different capacity, and hence healing power items to be added
	    depletionRate = (float) 0.25;
	    
	    //If initial level isn't empty, we can use it to oil droids
	    if (initialLevel != 0) {
	    	capabilities.add(Capability.EMPTIABLE);
	    }
	}
	
	public void empty() {
		int emptyAmount = getEmptiedAmount();
		level -= emptyAmount;
		// Remove EMPTIABLE once Canteen is empty!
		if (isEmpty()) {
			capabilities.remove(Capability.EMPTIABLE);
		}
	}
	/*
	 * Method added to get amount emptied at each emptying attempt
	 * Uses depletion rate, if amount in can < depletion rate
	 * Empty by that amount instead
	 * 
	 */
	public int getEmptiedAmount() {
		int emptyAmount = (int) Math.floor(depletionRate * capacity);
		if (level - emptyAmount < 0) {
			emptyAmount = level;
			}
		return emptyAmount;
	}
	
	/*
	 * Method added to check if the can is empty or not
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
