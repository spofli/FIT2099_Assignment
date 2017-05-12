package starwars.entities.actors;

import edu.monash.fit2099.simulator.space.Direction;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Capability;
import starwars.SWLocation;
import starwars.SWWorld;
import starwars.actions.Disassemble;
import starwars.actions.Move;
import starwars.actions.Oil;
import starwars.actions.Repair;
import starwars.actions.Take;
import starwars.entities.actors.behaviors.AttackInformation;
import starwars.entities.actors.behaviors.Follow;
import starwars.entities.actors.behaviors.Patrol;
import starwars.entities.actors.behaviors.RepairDroid;
import starwars.entities.actors.behaviors.TerrainDamage;
import starwars.entities.actors.behaviors.AttackNeighbours;

/**
 * Class to implement the repair droid R2-D2
 * 
 * R2-D2 will perform a <code>Repair</code>, <code>Oil</code>, <code>Disassemble</code> actions
 * in that priority order if they are possible.
 * This happens even if R2-D2 has an owner
 * 
 * If unowned and non of the above actions are possible, R2-D2 patrols a set path:
 * 5 spaces east then 5 spaces west.
 * 
 * @author Daryl Ho
 *
 */
public class R2D2 extends Droid {
	
	private Patrol path;
	
	/**
	 * Constructor for the <code>R2D2</code> class. This constructor will,
	 * <ul>
	 * 	<li>Initialize the message renderer for <code>R2D2</code></li>
	 * 	<li>Initialize the world for this <code>R2D2</code></li>
	 *  <li>Initialize the <code>Team</code> for <code>R2D2</code></li>
	 *  <li>Set the patrol path for <code>R2D2</code></li>
	 *  <li>Add the MECHANIC <code>Capability</code> to <code>R2D2</code></li>
	 * </ul>
	 * 
	 * @param m
	 * @param world
	 */
	public R2D2(MessageRenderer m, SWWorld world, Direction[] moves) {
		super(200, 0, "R2-D2", m, world);
		// TODO Auto-generated constructor stub
		path = new Patrol(moves);
		capabilities.add(Capability.MECHANIC);
	}
	@Override
	public void act() {
		if (isDead()) {
			return;
		}
		say(describeLocation());
		// R2-D2s performs droidRepairing operations before everything else
		AttackInformation r2Action;
		r2Action = RepairDroid.repairAction(this, this.world);
		if (r2Action != null) {
			if (r2Action.affordance instanceof Repair){
				say(getShortDescription() + " is preparing to repair something");
			}
			else if (r2Action.affordance instanceof Disassemble){
				say(getShortDescription() + " is preparing to disassemble something");
			}
			else if (r2Action.affordance instanceof Oil){
				say(getShortDescription() + " is preparing to oil something");
			}
			else if (r2Action.affordance instanceof Take){
				say(getShortDescription() + " is preparing to take something");
			}
			scheduler.schedule(r2Action.affordance, this, 1);
			return;
		}
				
		// If R2-D2 has an owner, follow it
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
		// If R2D2 has no owner, default command is patrolling
		Direction newdirection = path.getNext();
		say(getShortDescription() + " moves " + newdirection);
		Move myMove = new Move(newdirection, messageRenderer, world);
		scheduler.schedule(myMove, this, 1);
		TerrainDamage.BadlandsDroid(this, world);
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


