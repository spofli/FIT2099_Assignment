package starwars.entities.actors.behaviors;


import edu.monash.fit2099.simulator.matter.Affordance;
import starwars.actions.Leave;
import starwars.SWActor;
import starwars.SWEntityInterface;

/**
 * Behavior for <code>SWActors</code> to <code>Leave</code> their held item
 * 
 * @author Seolhyun95
 *
 */
public class DropItem {
	
	/**
	 * Checks for a carried item by the <code>SWActor</code>

	 * @param actor The <code>SWActor</code> that is drinking or dropping a <code>canteen</code>
	 * @return the  entity to <code>Leave</code> and the affordance.
	 */
	public static AttackInformation findCarriedItem(SWActor actor) {

		//Get item carried and leave affordance
		SWEntityInterface itemCarried = actor.getItemCarried();
		for (Affordance aff: itemCarried.getAffordances()) {
			if  (aff instanceof Leave) {
				Affordance leaveAffordance = aff;
				return new AttackInformation(itemCarried, leaveAffordance);
			}
		}	
		return null;
	}
}