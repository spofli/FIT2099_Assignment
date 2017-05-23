package starwars.entities.actors;

import edu.monash.fit2099.simulator.space.Direction;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWLegend;
import starwars.SWLocation;
import starwars.SWWorld;
import starwars.Team;
import starwars.actions.Move;
import starwars.entities.actors.behaviors.AttackInformation;
import starwars.entities.actors.behaviors.AttackNeighbours;
import starwars.entities.actors.behaviors.CheckLuke;
import starwars.entities.actors.behaviors.Follow;
import starwars.actions.MindControl;

/**
 * Princess Leia Organa 
 * 
 * 
 * @Daryl Ho
 *
 */
public class LeiaOrgana extends SWLegend {

	protected SWActor luke;
	private static LeiaOrgana leia = null; 
	
	private LeiaOrgana(MessageRenderer m, SWWorld world) {
		super(Team.GOOD, 200, 51, m, world);
		this.setShortDescription("Princess Leia");
		this.setLongDescription("Leia Organa, rebel princess");
		SWAffordance mindcontrol = new MindControl(this,m);
		this.addAffordance(mindcontrol);
		this.luke = null;
	}

	public static LeiaOrgana getLeiaOrgana(MessageRenderer m, SWWorld world) {
		leia = new LeiaOrgana(m, world);
		leia.activate();
		return leia;
	}
	
	private String describeLocation() {
		SWLocation location = this.world.getEntityManager().whereIs(this);
		return this.getShortDescription() + " [" + this.getHitpoints() + "] is at " + location.getShortDescription();

	}
	private void foundByLuke(SWActor lukeSkywalker) {
		// If Luke rescues Leia, set Luke to the SWActor instance of Luke instead of null
		leia.luke = lukeSkywalker;
	}
	
	
	@Override
	protected void legendAct() {
		
		if(isDead()) {
			return;
		}
		// If Leia has been rescued (player moves to her location)
		// She starts doing things, else she stays motionless..
		if (luke != null) {
			
			say(describeLocation());
			
			// Attacking takes precedence over following luke
			// Can be changed easily by reordering this block and the one below (*)
			AttackInformation attack = AttackNeighbours.attackLocals(leia, leia.world, true, true);
			if (attack != null) {
				say(getShortDescription() + " has attacked " + attack.entity.getShortDescription());
				scheduler.schedule(attack.affordance, leia, 1);
				return;
			}	
			// (*) Nothing to attack, follow Luke 
			Direction direction = Follow.followOwner(luke, leia, world);
			if (direction != null) {
				say(getShortDescription() + " follows " + luke.getShortDescription());
				Move myMove = new Move(direction, messageRenderer, world);
				scheduler.schedule(myMove, leia, 1);
				return;
			}
		}
		// Still imprisoned
		else {
			SWActor tempLuke = CheckLuke.findLuke(leia, leia.world);
			if (tempLuke != null) {
				foundByLuke(tempLuke);
				say("Rescued Leia! She now follows Luke");
				return;
			}
			
			say(getShortDescription() + " sits motionless on the Death Star");
		}
	}
}





