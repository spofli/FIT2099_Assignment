package starwars.entities.actors.behaviors;

import java.util.ArrayList;
import java.util.List;

import edu.monash.fit2099.simulator.matter.Affordance;
import edu.monash.fit2099.simulator.matter.EntityManager;
import starwars.SWActor;
import starwars.SWEntityInterface;
import starwars.SWLocation;
import starwars.SWWorld;
import starwars.actions.Attack;
import starwars.entities.actors.Droid;

/**
 * Behavior for <code>DarthVader</code> to force choke other <code>SWActors</code>
 * Searches for other actors to be force choked based on Attack affordance. Ignores <code>Droid</code>
 * 
 * @author XingHao
 *
 */
public class ForceChokeNeighbours {
	public static SWActor chokeLocals(SWActor actor, SWWorld world) {
		SWLocation location = world.getEntityManager().whereIs(actor);
		EntityManager<SWEntityInterface, SWLocation> em = world.getEntityManager();
		List<SWEntityInterface> entities = em.contents(location);

		// select the attackable things that are here that are not droids

		ArrayList<SWActor> canbechoked = new ArrayList<SWActor>();
		for (SWEntityInterface e : entities) {
			// Figure out if we should be using force choke
			if (e != actor && e instanceof SWActor) {
				for (Affordance a : e.getAffordances()) {
					if ((a instanceof Attack) && !(e instanceof Droid)) {
						canbechoked.add((SWActor) e);
						break;
					}
				}
			}
		}

		// if there's at least one thing we can choke, randomly choose
		// something to choke
		if (canbechoked.size() > 0) {
			return canbechoked.get((int) (Math.floor(Math.random() * canbechoked.size())));
		} else {
			return null;
		}
	}
}