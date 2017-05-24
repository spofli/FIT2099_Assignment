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
public class TurnLukeDark {
	/**
	 * Luke is on the same cell as <code>SWActor</code> calling this method
	 * @param actor <code>SWActor</code> using this method
	 * @param world <code>SWWorld</code> that <code>SWActor</code> is in

	 * @return <code>SWActor</code> that can be trained
	 */
	public static SWActor findLuke(SWActor actor, SWWorld world) {
		SWLocation location = world.getEntityManager().whereIs(actor);
		EntityManager<SWEntityInterface, SWLocation> em = world.getEntityManager();
		List<SWEntityInterface> entities = em.contents(location);

		// check if Luke is here
		SWActor a = null;
		for (SWEntityInterface e : entities) {
			if( e != actor && e instanceof SWActor ) {
				a = (SWActor) e;
				if (a.getShortDescription() == "Luke") {
					return a;
				}
			}
		}
		return null;
	}
}