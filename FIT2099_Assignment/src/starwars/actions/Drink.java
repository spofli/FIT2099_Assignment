package starwars.actions;

import starwars.SWAffordance;
import edu.monash.fit2099.simulator.matter.Affordance;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Capability;
import starwars.SWActionInterface;
import starwars.SWActor;
import starwars.SWEntityInterface;
import starwars.entities.Emptiable;

/**
 * An Affordance for drinking from a filled container of water
 * 
 * The affordance is offered by entities that can be drank from and can only
 * be applied in partnership with an <code>Entity</code> that is  <code>EMPTIABLE</code>
 * and implements the <code>Empty</code> interface.
 * 
 *
 * @author Seolhyun95
 */
public class Drink extends SWAffordance implements SWActionInterface {

	public Drink(SWEntityInterface theTarget, MessageRenderer m) {
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
		if (item!= null) {
			return item.hasCapability(Capability.EMPTIABLE);
		}
		return false;
	}

	@Override
	public void act(SWActor a) {
		SWEntityInterface item = a.getItemCarried();
		assert(item instanceof Emptiable);

		for(Affordance aff: item.getAffordances()) {
			if (aff instanceof Empty) {
				aff.execute(a);
			}
		}
		// Healing happens here
		// Heal by amount depleted ~
		Emptiable container = (Emptiable) this.getTarget();
		int emptiedAmount = container.getEmptiedAmount();
		a.healDamage(emptiedAmount);
		
		a.say(a.getShortDescription() + " drank from " + target.getLongDescription());
		a.say(item.getShortDescription() + "has been depleted by " + Integer.toString(emptiedAmount));
	}
	
	@Override
	public String getDescription() {
		return "drink from " + target.getShortDescription();
	}
}

