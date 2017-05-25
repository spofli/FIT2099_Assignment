package starwars.actions;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActionInterface;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;
import starwars.entities.actors.behaviors.AttackInformation;
import starwars.entities.actors.behaviors.AttackNeighbours;

/**
 * Command to learn from entities.
 * 
 * This affordance is attached to all entities that can teach
 * 
 * @author David.Squire@monash.edu (dsquire)
 */
public class Learn extends SWAffordance implements SWActionInterface {
	/**
	 * Constructor for the <code>Learn</code> class. Will initialize the <code>messageRenderer</code>
	 * 
	 * @param theTarget the target teaching
	 * @param m message renderer to display messages
	 */
	public Learn(SWEntityInterface theTarget, MessageRenderer m) {
		super(theTarget, m);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Returns the time is takes to perform this <code>Learn</code> action.
	 * 
	 * @return The duration of the Learn action. Currently hard coded to return 1.
	 */
	@Override
	public int getDuration() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public int getPriority() {
		// TODO Auto-generated method stub
		return 1;
	}
	
	/**
	 * Determine whether a particular <code>SWActor a</code> can attack the target.
	 * 
	 * @author 	spofli
	 * @param 	a the <code>SWActor</code> being queried
	 * @return 	true if <code>SWActor</code> does not have max force and there is no
	 * enemies around this <code>SWActor a</code>
	 */
	@Override
	public boolean canDo(SWActor a) {
		AttackInformation attack;
		attack = AttackNeighbours.attackLocals(a,  a.getWorld(), true, true);
		return (a.getForcepoints() < 100 && attack == null);
	}
	
	/**
	 * Perform the <code>Learn</code> command on an entity.
	 * <p>
	 * 
	 * @author 	spofli
	 * @param 	a the <code>SWActor</code> who is Learning
	 * @pre 	this method should only be called if the <code>SWActor a</code> is alive
	 * @pre		a <code>Learn</code> must not be performed if target is dead <code>SWActor</code>
	 * @post	if a <code>SWActor</code> reaches more than max force during a <code>Learn</code> their force should be set to 100
	 * @see		starwars.SWActor#isDead()
	 * @see 	starwars.Team
	 */
	@Override
	public void act(SWActor a) {
		SWEntityInterface target = this.getTarget();
		SWActor targetActor = (SWActor) target;
		a.say(a.getShortDescription() + " received training from " + targetActor.getShortDescription());
		a.setForce((a.getForcepoints()) + (int)(targetActor.getForcepoints()/5));
		if (a.getForcepoints() >= 100) { //higher than forcepoints cap
			targetActor.say(targetActor.getShortDescription() + " says: You are now one with the force, " + a.getShortDescription());
			a.setForce(100);
		}
	}

	/**
	 * A String describing what this <code>Learn</code> action will do, suitable for display on a user interface
	 * 
	 * @return String comprising "receive training from " and the short description of the target of this <code>Affordance</code>
	 */
	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "receive training from " + target.getShortDescription();
	}
}
