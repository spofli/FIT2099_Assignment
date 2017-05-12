package starwars.entities.actors.behaviors;

import java.util.ArrayList;
import java.util.List;

import edu.monash.fit2099.simulator.matter.Affordance;
import edu.monash.fit2099.simulator.matter.EntityManager;
import starwars.SWActor;
import starwars.SWEntityInterface;
import starwars.SWLocation;
import starwars.SWWorld;
import starwars.actions.Take;
import starwars.entities.Canteen;

/**
 * Behavior for <code>Ben</code> to pickup <code>canteens</code> that aren't empty
 * 
 * @author Seolhyun95
 *
 */
public class takeCanteens {


	/**
	 * Checks if there is a non-empty <code>Canteen</code> on the ground.
	 * Returns the <code>Canteen</code> and it's <code>Take</code> affordance.
	 * 
	 * @param actor The <code>SWActor</code> that is picking up the <code>Canteen</code>
	 * @param world The <codE>SWWorld</code> the actor is in
	 * @return
	 */
	public static AttackInformation findCanteens(SWActor actor, SWWorld world) {
		SWLocation location = world.getEntityManager().whereIs(actor);
		EntityManager<SWEntityInterface, SWLocation> em = world.getEntityManager();
		List<SWEntityInterface> entities = em.contents(location);
		
		// find canteens that exist!
		ArrayList<AttackInformation> canteens = new ArrayList<AttackInformation>();
		for (SWEntityInterface e: entities) {
			// Check if there are any non-empty canteens around
			if ( e!= actor && (e instanceof Canteen)) {
				for (Affordance a : e.getAffordances()) {
					if (a instanceof Take){
						if (!((Canteen) e).isEmpty()) {
							canteens.add(new AttackInformation(e, a));
							break;
						}
					}
				}
			}
		}
		// If there's a non-empty canteen, pick one up
		if (canteens.size() > 0) {
			return canteens.get((int) (Math.floor(Math.random() * canteens.size())));
		} else {
			return null;
		}
	}
}
	
			


	
	

