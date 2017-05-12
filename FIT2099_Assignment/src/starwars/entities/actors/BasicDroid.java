package starwars.entities.actors;

import edu.monash.fit2099.simulator.space.Direction;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWLocation;
import starwars.SWWorld;
import starwars.actions.Move;
import starwars.entities.actors.behaviors.Follow;
import starwars.entities.actors.behaviors.TerrainDamage;
import starwars.entities.actors.behaviors.AttackInformation;
import starwars.entities.actors.behaviors.AttackNeighbours;

/**
 * BasicDroid class to implement <code>Droids</code> that stand still without owners.
 * Has no special actions
 * 
 * @author Seolhyun95
 */
public class BasicDroid extends Droid {

	/**
	 * Constructor for the <code>BasicDroid</code> class. This constructor will,
	 * <ul>
	 * 	<li>Initialize the message renderer for the <code>BasicDroid</code> </li>
	 * 	<li>Initialize the world for this <code>BasicDroid</code> </li>
	 * 	<li>Initialize the hit points for this <code>BasicDroid</code> </li>
	 * 	<li>Initialize the <code>Team</code> for this <code>BasicDroid</code> </li>
	 * </ul>
	 * 
	 * @param hitpoints the hit points of this <code>BasicDroid</code> to get started with
	 * @param m <code>MessageRenderer</code> to display messages.
	 * @param world the <code>SWWorld</code> world to which this <code>BasicDroid</code> belongs to
	 * 
	 */
	public BasicDroid(int hitpoints, String name, MessageRenderer m, SWWorld world) {
		super(hitpoints, name, m, world);
		// TODO Auto-generated constructor stub
		
	}
	@Override
	public void act() {
		if (isDead() || getIsDisabled()) {
			return;
		}
		say(describeLocation());
		
		// If a droid has an owner, follow it
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
		// If the droid has no owner, default command is staying put
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


