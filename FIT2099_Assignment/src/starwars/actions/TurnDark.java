package starwars.actions;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActionInterface;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;
import starwars.Team;
/**
 * Command to turn Actors to dark side
 * @author XingHao
 *
 */
public class TurnDark extends SWAffordance implements SWActionInterface {

	public TurnDark(SWEntityInterface theTarget, MessageRenderer m) {
		super(theTarget, m);
		// TODO Auto-generated constructor stub
	}
	
/**
 * This ensures this action will only be done on actors
 * 
 */
	@Override
	public boolean canDo(SWActor a) {
		// TODO Auto-generated method stub
		SWEntityInterface target = this.getTarget();
		return target instanceof SWActor;
	}
	/**
	 * Checks <code>SWActor target</code>'s forcelevel to determine if this <code>SWActor</code>
	 * can resist being turned to dark side. This method will then turn the target to the dark side
	 * or do nothing if target manages to resist it
	 * 
	 */
	@Override
	public void act(SWActor a) {
		SWEntityInterface target = this.getTarget();
		SWActor targetActor = (SWActor) target;
		
		if (targetActor.getForcepoints() >= 100 && Math.random() >= 0.25) {
			target.say(target.getShortDescription() + "says: Nice try " + a.getShortDescription());
		}
		else {
			a.say(a.getShortDescription() + " has turned " + target.getShortDescription() + " to the Dark Side");
			targetActor.setTeam(Team.EVIL);
		}
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return " turn " + target.getShortDescription() + " to the Dark Side";
	}

}
