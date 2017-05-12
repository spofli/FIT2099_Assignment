package starwars.entities.actors.behaviors;

import java.util.List;

import edu.monash.fit2099.simulator.matter.Affordance;
import edu.monash.fit2099.simulator.matter.EntityManager;
import starwars.SWActor;
import starwars.SWEntityInterface;
import starwars.SWLocation;
import starwars.SWWorld;
import starwars.actions.Take;
import starwars.entities.LightSaber;

/**
 * Behavior to <code>Take</code> a <code>LightSaber</code> if on the same 
 * <code>Location</code> as the <code>SWActor</code>
 * 
 * @author Seolhyun95
 *
 */
public class PickLightsaber {

	/**
	 * Checks if there is a <code>LightSaber</code> on the ground.
	 * Returns the <code>LightSaber</code> and it's <code>Take</code> affordance.
	 * 
	 * @param actor The <code>SWActor</code> that is picking up the <code>LightSaber</code>
	 * @param world The <codE>SWWorld</code> the actor is in
	 * @return
	 */
	public static AttackInformation findLightsaber(SWActor actor, SWWorld world) {
		SWLocation location = world.getEntityManager().whereIs(actor);
		EntityManager<SWEntityInterface, SWLocation> em = world.getEntityManager();
		List<SWEntityInterface> entities = em.contents(location);
		

		// find lightsaber
		for (SWEntityInterface e: entities) {
			// Check if there are any lightsabers
			if ( e!= actor && (e instanceof LightSaber)) {
				for (Affordance a : e.getAffordances()) {
					if (a instanceof Take) {	
							return new AttackInformation(e, a);
					}
				}
			}
		}
		return new AttackInformation(null, null);
		}
	}
	
	
	
			


	