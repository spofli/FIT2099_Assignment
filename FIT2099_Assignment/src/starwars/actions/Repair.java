package starwars.actions;

import starwars.SWAffordance;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Capability;
import starwars.SWActionInterface;
import starwars.SWActor;
import starwars.SWEntityInterface;
import starwars.entities.DroidParts;
import starwars.entities.actors.Droid;

/**
 * An Affordance for repairing a disabled <code>Droid</code>.
 * 
 * The affordance is offered by entities that can be repaired.
 * Only offered when disabled
 *
 * @author Seolhyun95
 */
public class Repair extends SWAffordance implements SWActionInterface {

	public Repair(SWEntityInterface theTarget, MessageRenderer m) {
		super(theTarget, m);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getDuration() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getPriority() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public boolean isMoveCommand() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canDo(SWActor a) {
		SWEntityInterface item = a.getItemCarried();
		if (a.hasCapability(Capability.MECHANIC) && (item instanceof DroidParts)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void act(SWActor a) {
		SWEntityInterface item = a.getItemCarried();
		assert(item instanceof DroidParts);

		// Repair brings the droid's health to full
		// Currently implemented with an ncredibly large heal amount
		int heal = 99999;
		Droid droid = (Droid) target;
		droid.healDamage(heal);
		droid.enable();
		
		// Consume DroidParts
		a.setItemCarried(null);
	
		a.say(a.getShortDescription() + " has repaired " + target.getLongDescription());
		a.say(item.getShortDescription() + " has been consumed");
	}
	
	@Override
	public String getDescription() {
		return "repair  " + target.getShortDescription();
	}
}

