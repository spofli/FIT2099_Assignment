package starwars.actions;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Capability;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;
import starwars.entities.Emptiable;

/**
 * An Affordance for containers that can be emptied
 *
 * @author Daryl Ho
 */
public class Empty extends SWAffordance {

	public Empty(SWEntityInterface theTarget, MessageRenderer m) {
		super(theTarget, m);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean canDo(SWActor a) {
		return false;
	}

	@Override
	public void act(SWActor a) {
		// TODO Auto-generated method stub
		assert(this.getTarget().hasCapability(Capability.EMPTIABLE));
		Emptiable EmptiableTarget = (Emptiable) (this.getTarget());
		EmptiableTarget.empty();
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "";
	}

}
