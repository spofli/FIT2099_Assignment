package starwars.entities.actors;

import java.util.ArrayList;
import java.util.List;

import edu.monash.fit2099.gridworld.Grid;
import edu.monash.fit2099.simulator.space.Direction;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWEntityInterface;
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
	
	private boolean checkedforluke = false;
	
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
	
	/**
	 * Method to check for Luke and his team. Mon Mothma will only say farmboy line the first time she sees Luke.
	 * Or when Luke goes away and comes back.
	 * @param checked check if Mon Mothma already saw Luke
	 * @return boolean true if luke is around, false otherwise
	 */
	private boolean seesLuke(boolean checked) {
		boolean luke = false;
		boolean r2 = false;
		boolean leia = false;
		
		ArrayList<Direction> possibledirections = new ArrayList<Direction>();

		// build a list of available directions
		for (Grid.CompassBearing d : Grid.CompassBearing.values()) {
			if (SWWorld.getEntitymanager().seesExit(this, d)) {
				possibledirections.add(d);
			}
		}

		for (Direction d : possibledirections) {
			SWLocation neighbourLoc = (SWLocation) SWWorld.getEntitymanager().whereIs(this).getNeighbour(d);
			List<SWEntityInterface> entities = SWWorld.getEntitymanager().contents(neighbourLoc);
			if (entities != null) {
				for (SWEntityInterface e : entities) {
					if (e instanceof Player) {
						luke = true;
					}
					if (e instanceof R2D2) {
						r2 = true;
					}
					if (e instanceof LeiaOrgana) {
						leia = true;
					}
				}
			}
		}
		if (checked && luke) {
			return true;
		}
		else if (checked && !luke) {
			return false;
		}
		if (luke) {
			if (r2 && leia) {
				scheduler.endGame("WIN");
				return true;
			}
			else {
				say("What are you doing here, farmboy? Bring us General Organa and the plans!");
				return true;
			}
		}
		return false;
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
		// Look at surroundings for Luke
		checkedforluke = seesLuke(checkedforluke);
	}
}


