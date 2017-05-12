package starwars.actions;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActionInterface;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;

public class MindControl extends SWAffordance implements SWActionInterface {
	/**
	 * Constructor for the <code>MindControl</code> class. Will initialize the <code>messageRenderer</code>
	 * 
	 * @param theTarget the target teaching
	 * @param m message renderer to display messages
	 */
	public MindControl(SWEntityInterface theTarget, MessageRenderer m) {
		super(theTarget, m);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Returns the time is takes to perform this <code>MindControl</code> action.
	 * 
	 * @return The duration of the MindControl action. Currently hard coded to return 1.
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
	 * Determine whether a particular <code>SWActor a</code> can attempt mindcontrol the target.
	 * 
	 * @author 	spofli
	 * @param 	a the <code>SWActor</code> being queried
	 * @return 	true if <code>SWActor</code> is alive and <code>SWActor</code> has more than 20 forcepoints
	 */
	@Override
	public boolean canDo(SWActor a) {
		SWEntityInterface target = this.getTarget();
		SWActor targetActor = (SWActor) target;
		// TODO Auto-generated method stub
		return (a.getForcepoints() >= 20 && !targetActor.isMindControlled() && !targetActor.isDead());
	}

	/**
	 * Perform the <code>MindControl</code> command on an entity.
	 * <p>
	 * 
	 * @author 	spofli
	 * @param 	a the <code>SWActor</code> who is using mind control
	 * @pre 	this method should only be called if the <code>SWActor a</code> is alive
	 * @pre		a <code>MindControl</code> must not be performed if target is dead or already mind controlled <code>SWActor</code>
	 * @see 	starwars.Team
	 */
	@Override
	public void act(SWActor a) {
		SWEntityInterface target = this.getTarget();
		SWActor targetActor = (SWActor) target;
		a.say(a.getShortDescription() + " attempts to mind control " + target.getShortDescription());
		if (targetActor.getForcepoints()*2 >= a.getForcepoints()) {
			a.say("\t" + a.getShortDescription() + "'s force is too weak to mind control " + target.getShortDescription());
		}
		else {
			a.say(a.getShortDescription() + " uses mind control on " + target.getShortDescription());
			targetActor.setMindControl(true);
		}
	}

	/**
	 * A String describing what this <code>MindControl</code> action will do, suitable for display on a user interface
	 * 
	 * @return String comprising "attempt to mind control " and the short description of the target of this <code>Affordance</code>
	 */
	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "attempt to mind control " + target.getShortDescription();
	}

}
