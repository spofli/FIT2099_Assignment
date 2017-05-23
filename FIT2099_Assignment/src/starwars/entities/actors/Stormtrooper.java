package starwars.entities.actors;

import java.util.ArrayList;

import edu.monash.fit2099.gridworld.Grid;
import edu.monash.fit2099.simulator.space.Direction;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWLocation;
import starwars.SWWorld;
import starwars.Team;
import starwars.actions.CallBackup;
import starwars.actions.MindControl;
import starwars.actions.Move;
import starwars.entities.Blaster;
import starwars.entities.actors.behaviors.AttackInformation;
import starwars.entities.actors.behaviors.AttackNeighbours;
import starwars.entities.actors.behaviors.MindControlled;

public class Stormtrooper extends SWActor {

	private String name;

	/**
	 * Create a Stormtrooper. Stormtroopers will randomly wander
	 * around the playfield. And have a 5% chance of calling for backup.
	 * They will attack actors of different team but have terrible aim
	 * 
	 * @param hitpoints
	 *            the number of hit points of this Stormtrooper. If this
	 *            decreases to below zero, the Raider will die.
	 *            
	 * @param forcepoints
	 * 			  the number of force points of this Stormtrooper. Max is 100
	 * @param name
	 *            this raider's name. Used in displaying descriptions.
	 * @param m
	 *            <code>MessageRenderer</code> to display messages.
	 * @param world
	 *            the <code>SWWorld</code> world to which this
	 *            <code>Stormtrooper</code> belongs to
	 * 
	 */
	public Stormtrooper(int hitpoints, int forcepoints, String name, MessageRenderer m, SWWorld world) {
		super(Team.EVIL, hitpoints, forcepoints, m, world);
		// TODO Auto-generated constructor stub
		this.name = name;
		setItemCarried(new Blaster(m));
		SWAffordance mindcontrol = new MindControl(this,m);
		this.addAffordance(mindcontrol);
	}

	@Override
	public void act() {
		if (isDead()) {
			return;
		}
		say(describeLocation());
		AttackInformation attack = AttackNeighbours.attackLocals(this, this.world, true, true);
		
		if (this.isMindControlled()) {
			Direction direction = MindControlled.getDirection(this);
			Move myMove = new Move(direction, messageRenderer, world);
			say(this.getShortDescription() + " feels like moving " + direction);
			scheduler.schedule(myMove, this, 1);
		}
		else if (attack != null && Math.random() < 0.25) { //0.25 chance to attack
			if (Math.random() < 0.25) { // 0.25 chance to hit 
				say(getShortDescription() + " has attacked " + attack.entity.getShortDescription());
				scheduler.schedule(attack.affordance, this, 1);
			}
			else {
				say(getShortDescription() + " shoots wildly!");
			}
		}
		else if (Math.random() > 0.95) { // 0.05 chance to call for backup
			say(this.getShortDescription() + " calls for backup");
			CallBackup callbackup = new CallBackup(messageRenderer);
			scheduler.schedule(callbackup, this, 1);
		}
		
		else {
			
			ArrayList<Direction> possibledirections = new ArrayList<Direction>();

			// build a list of available directions
			for (Grid.CompassBearing d : Grid.CompassBearing.values()) {
				if (SWWorld.getEntitymanager().seesExit(this, d)) {
					possibledirections.add(d);
				}
			}

			Direction heading = possibledirections.get((int) (Math.floor(Math.random() * possibledirections.size())));
			say(getShortDescription() + " is heading " + heading + " next.");
			Move myMove = new Move(heading, messageRenderer, world);

			scheduler.schedule(myMove, this, 1);
		}
	}

	@Override
	public String getShortDescription() {
		return name + " the Stormtrooper";
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
