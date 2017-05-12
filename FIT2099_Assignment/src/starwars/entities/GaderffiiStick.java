package starwars.entities;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Capability;
import starwars.SWEntity;
import starwars.actions.Take;

/**
 * Bladed club used by the Tusken Raiders that implements the Weapon capability
 * 
 * It does not take damage with use.
 * 

 * 
 *  @author spofli
 *  @see {@link starwars.actions.Attack}
 */

public class GaderffiiStick extends SWEntity {

	/**
	 * Constructor for the <code>GaderffiiStick</code> class. This constructor will,
	 * <ul>
	 * 	<li>Initialize the message renderer for the <code>GaderffiiStick</code></li>
	 * 	<li>Set the short description of this <code>GaderffiiStick</code>>
	 * 	<li>Set the long description of this <code>GaderffiiStick</code> 
	 * 	<li>Add a <code>Take</code> affordance to this <code>GaderffiiStick</code> so it can be taken</li> 
	 * </ul>
	 * 
	 * @param m <code>MessageRenderer</code> to display messages.
	 * 
	 * @see {@link starwars.actions.Take}
	 * @see {@link starwars.Capability}
	 * @see {@link starwars.actions.Chop} 1
	 */
	public GaderffiiStick(MessageRenderer m) {
		super(m);
		
		this.shortDescription = "A Gaderffii Stick";
		this.longDescription = "GaderffiiStick.  Doesn't look very strong";
		this.hitpoints = 10; // weak stuff
		
		this.addAffordance(new Take(this, m));//add the take affordance so that the LightSaber can be taken by SWActors
		this.capabilities.add(Capability.WEAPON);// it's a weapon.  
	}
	
	/**
	 * Gaderffii Sticks are bladed, so doing damage to them causes the blade to go blunt
	 * @param damage - the amount of damage that would be inflicted on a non-mystical Entity
	 */
	@Override
	public void takeDamage(int damage) {
		super.takeDamage(damage);
		
		if (this.hitpoints<=0) {
			this.shortDescription = "a blunt gaderffii stick";
			this.longDescription  = "A blunt gaderffii stick that has no use. Or perhaps as a walking stick";
			
			this.capabilities.remove(Capability.WEAPON);
		}

	}
	
	/**
	 * A symbol that is used to represent the GaderfiiStick on a text based user interface
	 * 
	 * @return 	A String containing a single character.
	 * @see 	{@link starwars.SWEntityInterface#getSymbol()}
	 */
	@Override
	public String getSymbol() {
		return "{";
	}
	
	

}