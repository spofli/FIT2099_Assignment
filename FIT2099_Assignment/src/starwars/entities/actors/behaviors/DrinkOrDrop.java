package starwars.entities.actors.behaviors;


import edu.monash.fit2099.simulator.matter.Affordance;
import starwars.actions.Drink;
import starwars.actions.Leave;
import starwars.SWActor;
import starwars.SWEntityInterface;

/**
 * Behavior made for an <code>SWActor</code> to drink from a <code>Canteen</code> or drop it if it's empty
 * So far only includes Ben Kenobi 
 * 
 * @author Daryl Ho
 *
 */
public class DrinkOrDrop {
	
	/**
	 * Checks for a drinkable <code>Canteen</code> from the <code>SWActor</code>
	 * Checks if it is empty
	 * Then returns the canteen and affordance to match drinking or dropping
	 * 
	 * @param actor The <code>SWActor</code> that is drinking or dropping a <code>canteen</code>
	 * @return the <code>Canteen></code> entity to <code>Drink</code> or drop and the affordance.
	 */
	public static AttackInformation findDrinkable(SWActor actor) {

		//Get item carried and drink affordance
		//Gets leave affordance if emptied
		SWEntityInterface itemCarried = actor.getItemCarried();
		for (Affordance aff: itemCarried.getAffordances()) {
			if  (aff instanceof Drink) {
				Affordance leaveAffordance = aff;
				return new AttackInformation(itemCarried, leaveAffordance);
			}
		}	
		for (Affordance aff: itemCarried.getAffordances()) {
			if  (aff instanceof Leave) {
				Affordance leaveAffordance = aff;
				return new AttackInformation(itemCarried, leaveAffordance);
			}
		}	
		return null;
	}
}