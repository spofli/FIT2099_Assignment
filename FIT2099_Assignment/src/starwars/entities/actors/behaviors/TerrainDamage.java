package starwars.entities.actors.behaviors;

import starwars.SWActor;
import starwars.SWLocation;
import starwars.SWWorld;
import starwars.entities.actors.Droid;


/**
 * Behavior for <code>Droids</code> to take damage if moving in Badlands
 * 
 * @author Seolhyun95
 *
 */
public class TerrainDamage{
	
	/**
	 * Check if the <code>Droid</code> is in Badlands when moving
	 * Does damage to the <code>Droid</code> if so.
	 * 
	 * @param actor The <code>Droid</code> that is moving
	 * @param world The <code>SWWorld</code> the <code>Droid</code> is in.
	 */
	public static void BadlandsDroid(SWActor actor, SWWorld world) {
		// Check if in Badlands
		// Take damage if so
		// Get Disabled if hitpoints drop to 0
		SWLocation location = world.getEntityManager().whereIs(actor);
		Droid droid = (Droid) actor;
		if (location.getSymbol() == 'b') {
			droid.takeDamage(5);
			droid.say(droid.getShortDescription() + " took damage from moving in Badlands!");
			if (droid.getHitpoints() <= 0) {
				droid.disable();
			}
		}
	}
}
			


