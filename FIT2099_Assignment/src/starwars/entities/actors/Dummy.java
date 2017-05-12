package starwars.entities.actors;

import edu.monash.fit2099.simulator.space.Direction;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWLocation;
import starwars.SWWorld;
import starwars.Team;
import starwars.actions.MindControl;
import starwars.actions.Move;
import starwars.entities.actors.behaviors.MindControlled;


public class Dummy extends SWActor {

	private String name;

	/**
	 * Create a Dummy.  Dummys will stand still. Dummys
	 * are all members of team GOOD, so their attempts to attack
	 * other Dummys won't be effectual.
	 * 
	 * @param hitpoints
	 *            the number of hit points of this Dummy. If this
	 *            decreases to below zero, the Raider will die.
	 * @param forcepoints
	 * 			  the number of force points of this Dummy. Max is 100
	 * @param name
	 *            this raider's name. Used in displaying descriptions.
	 * @param m
	 *            <code>MessageRenderer</code> to display messages.
	 * @param world
	 *            the <code>SWWorld</code> world to which this
	 *            <code>TuskenRaider</code> belongs to
	 * 
	 */
	public Dummy(int hitpoints, int forcepoints, String name, MessageRenderer m, SWWorld world) {
		super(Team.GOOD, hitpoints, forcepoints, m, world);
		// TODO Auto-generated constructor stub
		this.name = name;
		SWAffordance mindcontrol = new MindControl(this,m);
		this.addAffordance(mindcontrol);
	}

	@Override
	public void act() {
		if (isDead()) {
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

	@Override
	public String getShortDescription() {
		return name;
	}

	@Override
	public String getLongDescription() {
		return this.getShortDescription();
	}

	private String describeLocation() {
		SWLocation location = this.world.getEntityManager().whereIs(this);
		return this.getShortDescription() + " [" + this.getHitpoints() + "] is at " + location.getShortDescription();

	}
}
