package starwars.actions;

import edu.monash.fit2099.simulator.matter.Affordance;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActionInterface;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;

public class ForceChoke extends SWAffordance implements SWActionInterface {

	
	/**
	 * Constructor for the <code>ForceChoke</code> class. Will initialize the <code>messageRenderer</code> and
	 * give <code>force choke</code> a priority of 1 (lowest priority is 0).
	 * 
	 * @param theTarget the target being force choked
	 * @param m message renderer to display messages
	 */
	public ForceChoke(SWEntityInterface theTarget, MessageRenderer m) {
		super(theTarget, m);	
		priority = 1;
	}


	/**
	 * Returns the time is takes to perform this <code>force choke</code> action.
	 * 
	 * @return The duration of the force choke action. Currently hard coded to return 1.
	 */
	@Override
	public int getDuration() {
		return 1;
	}

	
	/**
	 * A String describing what this <code>force choke</code> action will do, suitable for display on a user interface
	 * 
	 * @return String comprising "force choke " and the short description of the target of this <code>Affordance</code>
	 */
	@Override
	public String getDescription() {
		return "force choke " + this.target.getShortDescription();
	}


	/**
	 * Determine whether a particular <code>SWActor a</code> can force choke the target.
	 * 
	 * @author 	dsquire
	 * @param 	a the <code>SWActor</code> being queried
	 * @return 	true any <code>SWActor</code> can always try an force choke, it just won't do much 
	 * 			good unless this <code>SWActor a</code> has a suitable weapon.
	 */
	@Override
	public boolean canDo(SWActor a) {
		return true;
	}

	
	/**
	 * Perform the <code>ForceChoke</code> command on an entity.
	 * <p>
	 * This method does not perform any damage (an force choke) if,
	 * <ul>
	 * 	<li>The target of the <code>ForceChoke</code> is a <code>Droid</code>
	 * </ul>
	 * <p>
	 * else it would damage the entity force choked
	 * 
	 * @param 	a the <code>SWActor</code> who is force choking
	 * @pre 	this method should only be called if the <code>SWActor a</code> is alive
	 * @pre		an <code>force choke</code> must not be performed on a dead <code>SWActor</code>
	 * @post	if a <code>SWActor</code>dies in an <code>ForceChoke</code> their <code>Attack</code> affordance would be removed
	 * @see		starwars.SWActor#isDead()
	 * @see 	starwars.Team
	 */
	@Override
	public void act(SWActor a) {
		SWEntityInterface target = this.getTarget();
		boolean targetIsActor = target instanceof SWActor;
		SWActor targetActor = null;
		
		if (targetIsActor) {
			targetActor = (SWActor) target;
		}
				
		a.say(a.getShortDescription() + " is force chokeing " + target.getShortDescription() + "!");
		target.takeDamage(50);
			
		//After the force choke
		if (targetActor.isDead()) {
			target.setLongDescription(target.getLongDescription() + ", that was killed by force choke");
						
			//remove the Attack affordance of the dead actor so it can no longer be force choked or attacked
			//remove Attack affordance because force choke depends on Attack affordance
			for (Affordance affordance : target.getAffordances()) {
				if (affordance instanceof Attack); {
					target.removeAffordance(affordance);
				}
				
			}
		}
	}
}
