package starwars.entities.actors;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWAffordance;
import starwars.SWLegend;
import starwars.SWLocation;
import starwars.SWWorld;
import starwars.Team;
import starwars.actions.MindControl;

/**
 * Admiral Ackbar
 * 
 * Admiral Ackbar is similar to a Dummy.
 * Does not really do anything but stand still and say "It's a trap"
 * No attacking in act() because there really is no way to bring any enemy to the rebel base
 * OR bring Ackbar out of the base.
 * Is a Legend because you can only make one.
 * Weak-minded and can be mindcontrolled. (Don't feel that this is important though)
 * 
 * @Daryl Ho
 *
 */
public class AdmiralAckbar extends SWLegend {

	private static AdmiralAckbar ackbar = null; 
	
	private AdmiralAckbar(MessageRenderer m, SWWorld world) {
		super(Team.GOOD, 200, 1, m, world);
		this.setShortDescription("Admiral Ackbar");
		this.setLongDescription("Admiral Ackbar, the Mon Calamari military commander of the rebel alliance");
		SWAffordance mindcontrol = new MindControl(this,m);
		this.addAffordance(mindcontrol);
	}

	public static AdmiralAckbar getAdmiralAckbar(MessageRenderer m, SWWorld world) {
		ackbar = new AdmiralAckbar(m, world);
		ackbar.activate();
		return ackbar;
	}
	
	private String describeLocation() {
		SWLocation location = this.world.getEntityManager().whereIs(this);
		return this.getShortDescription() + " [" + this.getHitpoints() + "] is at " + location.getShortDescription();

	}
	@Override
	protected void legendAct() {
		
		if(isDead()) {
			return;
		}
		say(describeLocation());
		if (Math.random() > 0.9){
			say("It's a trap!");
		}
		
		
	}
}

