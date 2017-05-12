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
 * Behavior for <code>droids</code> to follow their owner
 * Will follow owners if they are in the same or neighbouring <code>Location</code>
 * Else stays put and says that the <code>Droid</code> has lost sight of the owner
 * 
 * @author Seolhyun95
 *
 */
public class Follow {
	
	/**
	 * Returns a <code>Direction</code> to <code>Move</code> after checking neighbouring
	 * <code>Locations</code> for the owner of the <code>Droid</code>
	 * 
	 * @param owner the owner of the <code>Droid</code>
	 * @param droid the <code>Droid</code> that is following the owner
	 * @param world the <code>SWWorld</code> that the <code>Droid</code> is in
	 * @return
	 */
	public static Direction followOwner(SWActor owner, SWActor droid, SWWorld world) {
	
		EntityManager<SWEntityInterface, SWLocation> em = world.getEntityManager();
		SWLocation ownerLoc = em.whereIs(owner);
		SWLocation selfLoc = em.whereIs(droid);
		
		// If owner is at the same spot, make droid stay still
		if (ownerLoc == selfLoc) {
			droid.say(droid.getShortDescription() + " stays put.");
			return null;
		}
		// build a list of available directions
		ArrayList<Direction> possibledirections = new ArrayList<Direction>();
		
		for (Grid.CompassBearing d : Grid.CompassBearing.values()) {
			if (em.seesExit(droid, d)) {
				possibledirections.add(d);
			}
		}
		// If owner is at a neighbouring location, move there
		for (Direction d : possibledirections) {
			Location neighbourLoc = em.whereIs(droid).getNeighbour(d);
			if (neighbourLoc == ownerLoc) {
				return d;
			}
		}	
		// If owner not in neighbouring location, just stay put
		droid.say(droid.getShortDescription() + " couldn't find " + owner.getShortDescription() + " and stays put.");
		return null;	
	}
}
