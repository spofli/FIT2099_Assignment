package starwars.entities;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWEntity;
import starwars.actions.Take;

/**
 * Parts from a Droid
 * 
 * It can be used to repair disabled Droids.
 * Repairing consumes DroidParts
 * 
 * @author Seolhyun95 
 */

public class DroidParts extends SWEntity {
	
	/**
	 * Constructor for the <code>DroidParts</code> class. This constructor will
	 * <ul>
	 *  <li> set descriptions for the <ode>DroidParts</code> </li>
	 *  <li> add the <code>Take</code> affordance to the DroidParts </li>
	 * </ul>
	 * @param m the <code>MessageRenderer</code> used.
	 */
	public DroidParts(MessageRenderer m)  {
		super(m);
		this.shortDescription = "droid parts";
		this.longDescription = "robotic looking parts that make up a droid";
		this.addAffordance(new Take(this, m));//add the take affordance so that the DroidPart can be taken by SWActors
	}
	@Override 
	public String getShortDescription() {
		return shortDescription; 
	}
	
	@Override
	public String getLongDescription () {
		return longDescription;
	}
	/**
	 * A symbol that is used to represent the DroidParts on a text based user interface
	 * 
	 * @return 	A String containing a single character.
	 * @see 	{@link starwars.SWEntityInterface#getSymbol()}
	 */
	@Override
	public String getSymbol() {
		return "d";
	}
	
}
