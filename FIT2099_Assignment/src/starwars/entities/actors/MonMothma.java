package starwars.entities.actors;

import edu.monash.fit2099.simulator.space.Direction;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWLegend;
import starwars.SWLocation;
import starwars.SWWorld;
import starwars.Team;
import starwars.actions.Move;
import starwars.entities.actors.behaviors.MindControlled;

/**
 * MonMothma
 * 
 * Admiral Mothma is similar to a Dummy.
 * Does not really do anything but stand still and say things
 * No attacking in act() because there really is no way to bring any enemy to the rebel base
 * OR bring Mothma out of the base.
 * Is a Legend because you can only make one.
 * Weak-minded and can be mindcontrolled. (Don't feel that this is important though)
 * 
 * @Daryl Ho
 *
 */
public class MonMothma extends SWLegend {

	private static MonMothma mothma = null; 
	
	private MonMothma(MessageRenderer m, SWWorld world) {
		super(Team.GOOD, 200, 1, m, world);
		this.setShortDescription("Mon Mothma");
		this.setLongDescription("Mon Mothma, a prominent figure for the Rebel Alliance");
	}

	public static MonMothma getMonMothma(MessageRenderer m, SWWorld world) {
		mothma = new MonMothma(m, world);
		mothma.activate();
		return mothma;
	}
	
	private String describeLocation() {
		SWLocation location = this.world.getEntityManager().whereIs(this);
		return this.getShortDescription() + " [" + this.getHitpoints() + "] is at " + location.getShortDescription();
	}
	
	private void sayFarmboy(){
	// Function to say her line, triggered when Luke TRAVELS to Rebel HQ without the right team
		say("What are you doing here, farmboy? Bring us General Organa and the plans!");
	}
	
	@Override
	protected void legendAct() {
		
		if(isDead()) {
			return;
		}
		say(describeLocation());
		if (this.isMindControlled()) {
			Direction direction = MindControlled.getDirection(this);
			Move myMove = new Move(direction, messageRenderer, world);
			say(this.getShortDescription() + " feels like moving " + direction);
			scheduler.schedule(myMove, this, 1);
		}
	}
}


