package starwars.actions;

import starwars.SWAffordance;
import edu.monash.fit2099.simulator.matter.Affordance;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Capability;
import starwars.SWActionInterface;
import starwars.SWActor;
import starwars.SWEntityInterface;
import starwars.entities.Emptiable;
import starwars.entities.actors.Droid;
import starwars.entities.actors.R2D2;

/**
 * An Affordance for oiling a mechanical Droid.
 * 
 * The affordance is offered by entities that can be oiled(Droids).
 * Can only be applied in partnership with an <code>Entity</code> that is <code>EMPTIABLE</code>
 * and implements the <code>Empty</code> interface.
 * 
 * The <code>Entity</code> should also provide the oil capability.
 *
 * @author Daryl Ho
 */
public class Oil extends SWAffordance implements SWActionInterface {

	public Oil(SWEntityInterface theTarget, MessageRenderer m) {
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
		// Need special check for R2-D2's "INTERNAL RESERVOIR"
		if (a instanceof R2D2) {
			return true;
		}
		SWEntityInterface item = a.getItemCarried();
		if (item!= null) {
			return item.hasCapability(Capability.EMPTIABLE);
		}
		return false;
	}

	@Override
	public void act(SWActor a) {
		// Special section for R2-D2
		if (a instanceof R2D2) {
			int oilAmount = 25;
			Droid droid = (Droid) target;
			droid.healDamage(oilAmount);
			a.say(a.getShortDescription() + " has oiled " + target.getLongDescription());
			return;
		}
		
		SWEntityInterface item = a.getItemCarried();
		assert(item instanceof Emptiable);

		for(Affordance aff: item.getAffordances()) {
			if (aff instanceof Empty) {
				aff.execute(a);
			}
		}
		// Healing happens here
		// Heal by amount depleted ~
		Emptiable container = (Emptiable) item;
		int emptiedAmount = container.getEmptiedAmount();
		Droid droid = (Droid) target;
		droid.healDamage(emptiedAmount);
		
		a.say(a.getShortDescription() + " has oiled " + target.getLongDescription());
		a.say(item.getShortDescription() + "has been depleted by " + Integer.toString(emptiedAmount));
	}
	
	@Override
	public String getDescription() {
		return "oil  " + target.getShortDescription();
	}
}

