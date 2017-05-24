package starwars.entities.actors.behaviors;

import java.util.List;

import edu.monash.fit2099.simulator.matter.EntityManager;
import starwars.SWActor;
import starwars.SWEntityInterface;
import starwars.SWLocation;
import starwars.SWWorld;
/**
 * Class containing method trainLuke
 * @author XH
 *
 */
public class TrainTarget {
	/**
	 * Checks if trainable actor (Only Player for now) is on the same cell as <code>SWActor</code> calling this method
	 * @param actor <code>SWActor</code> using this method
	 * @param world <code>SWWorld</code> that <code>SWActor</code> is in
	 * @param avoidNonFriendlies boolean to check enemies
	 * @param avoidNonActors boolean to check non actors
	 * @return <code>SWActor</code> that can be trained
	 */
	public static SWActor trainLuke(SWActor actor, SWWorld world, boolean avoidNonFriendlies, boolean avoidNonActors) {
		SWLocation location = world.getEntityManager().whereIs(actor);
		EntityManager<SWEntityInterface, SWLocation> em = world.getEntityManager();
		List<SWEntityInterface> entities = em.contents(location);

		// select the trainable things that are here
		SWActor a = null;
		for (SWEntityInterface e : entities) {
			// Figure out if we should be training this entity
			if( e != actor && 
					(e instanceof SWActor && 
							(avoidNonFriendlies==false || ((SWActor)e).getTeam() == actor.getTeam()) 
					|| (avoidNonActors == false && !(e instanceof SWActor)))) {
				a = (SWActor) e;
				if (a.isJedi()) {
					return a;
				}
			}
		}
		return null;
	}
}
