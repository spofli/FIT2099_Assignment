package starwars.entities.actors.behaviors;

import java.util.ArrayList;


import edu.monash.fit2099.gridworld.Grid;
import edu.monash.fit2099.simulator.matter.EntityManager;
import edu.monash.fit2099.simulator.space.Direction;
import edu.monash.fit2099.simulator.space.Location;
import starwars.SWActor;
import starwars.SWEntityInterface;
import starwars.SWLocation;
import starwars.SWWorld;

/**
 * Behavior for <code>actors</code> to follow their owner
 * Will follow owners if they are in the same or neighbouring <code>Location</code>
 * Else stays put and says that the <code>actor</code> has lost sight of the owner
 * 
 * @author Daryl Ho
 *
 */
public class Follow {
	
	/**
	 * @Daryl H0 -23/5/17
	 * Changed Variable names and descriptions to make Follow more general, since
	 * It can be applied to more than just actors. NOTHING but name changes
	 * 
	 * Returns a <code>Direction</code> to <code>Move</code> after checking neighbouring
	 * <code>Locations</code> for the owner of the <code>SWActor</code>
	 * 
	 * @param owner the owner of the <code>SWActor</code>
	 * @param actor the <code>SWActor</code> that is following the owner
	 * @param world the <code>SWWorld</code> that the <code>SWActor</code> is in
	 * @return
	 */
	public static Direction followOwner(SWActor owner, SWActor actor, SWWorld world) {
	
		EntityManager<SWEntityInterface, SWLocation> em = world.getEntityManager();
		SWLocation ownerLoc = em.whereIs(owner);
		SWLocation selfLoc = em.whereIs(actor);
		
		// If owner is at the same spot, make actor stay still
		if (ownerLoc == selfLoc) {
			actor.say(actor.getShortDescription() + " stays put.");
			return null;
		}
		// build a list of available directions
		ArrayList<Direction> possibledirections = new ArrayList<Direction>();
		
		for (Grid.CompassBearing d : Grid.CompassBearing.values()) {
			if (em.seesExit(actor, d)) {
				possibledirections.add(d);
			}
		}
		// If owner is at a neighbouring location, move there
		for (Direction d : possibledirections) {
			Location neighbourLoc = em.whereIs(actor).getNeighbour(d);
			if (neighbourLoc == ownerLoc) {
				return d;
			}
		}	
		// If owner not in neighbouring location, just stay put
		actor.say(actor.getShortDescription() + " couldn't find " + owner.getShortDescription() + " and stays put.");
		return null;	
	}
}
