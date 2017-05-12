package starwars.actions;

import starwars.SWAffordance;
import edu.monash.fit2099.simulator.matter.Affordance;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActionInterface;
import starwars.SWActor;
import starwars.SWEntityInterface;
import starwars.entities.actors.Droid;

/**
 * An Affordance for taking ownership of a <code>Droid</code>
 * 
 * The affordance is offered by <code>Droid</code> that have no owners!
 *
 * @author Seolhyun95
 */
public class Own extends SWAffordance implements SWActionInterface {

	public Own(SWEntityInterface theTarget, MessageRenderer m) {
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
		// No restrictions since only the player will make an effort to own Droids.
		return true;
	}

	@Override
	public void act(SWActor a) {
		// Change to owner's team
		Droid droid = (Droid) target;;
		droid.setTeam(a.getTeam());
		droid.changeOwner(a);

		// Remove own affordance
		for (Affordance aff: droid.getAffordances()) {
			if (aff instanceof Own) {
				droid.removeAffordance(aff);
			}
		}
		a.say(a.getShortDescription() + " has become " + target.getLongDescription() + "\'s owner");
	}
	
	@Override
	public String getDescription() {
		return "take ownership of " + target.getShortDescription();
	}
}

