package starwars.entities.actors;

import edu.monash.fit2099.simulator.space.Direction;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWLocation;
import starwars.SWWorld;
import starwars.actions.Move;
import starwars.entities.actors.behaviors.AttackInformation;
import starwars.entities.actors.behaviors.Follow;
import starwars.entities.actors.behaviors.TerrainDamage;
import starwars.entities.actors.behaviors.AttackNeighbours;
import starwars.entities.actors.behaviors.C3Quotes;

/**
 * C3P0 class to implement C3-P0
 * 
 * Isn't much different than a <code>BasicDroid</code>
 * C3P0 only stays put if unowned.
 * Has a 10% of saying 5 random lines each turn
 * Hardcoded to have 200 hitpoints
 * 
 * @author Seolhyun95
 *
 */
public class C3P0 extends Droid {

	/**
	 * Constructor for the <code>C3P0</code> class. This constructor will,
	 * <ul>
	 * 	<li>Initialize the message renderer for <code>C3P0</code></li>
	 * 	<li>Initialize the world for this <code>C3P0</code></li>
	 *  <li>Initialize the <code>Team</code> for <code>C3P0</code></li>
	 * </ul>
	 * 
	 * @param m
	 * @param world
	 */
	public C3P0(MessageRenderer m, SWWorld world) {
		super(200, "C-3P0", m, world);
		// TODO Auto-generated constructor stub
		
	}
	@Override
	public void act() {
		if (isDead()) {
			return;
		}
		say(describeLocation());
		
		// C3P0 has a 10% chance of saying something
		if (Math.random() > 0.9) {
			String line = C3Quotes.getQuote();
			say(line);
		}
		
		// If C3P0 has an owner, follow it
		if (owner != null) {
			// Attacking takes precedence over following the owner 
			AttackInformation attack = AttackNeighbours.attackLocals(this, this.world, true, true);
			if (attack != null) {
				say(getShortDescription() + " has attacked " + attack.entity.getShortDescription());
				scheduler.schedule(attack.affordance, this, 1);
				return;
		}
			// If nothing to attack:
			Direction direction = Follow.followOwner(owner, this, world);
			if (direction != null) {
				say(getShortDescription() + " follows " + owner.getShortDescription());
				Move myMove = new Move(direction, messageRenderer, world);
				scheduler.schedule(myMove, this, 1);
				TerrainDamage.BadlandsDroid(this, world);
				return;
			}
		}
		// If C3P0 has no owner, default command is staying put 
		say(getShortDescription() + " is staying put.");
		return;
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


