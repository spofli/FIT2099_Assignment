package starwars.entities.actors;

import edu.monash.fit2099.simulator.space.Direction;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWLegend;
import starwars.SWWorld;
import starwars.Team;
import starwars.actions.Leave;
import starwars.actions.Move;
import starwars.entities.LightSaber;
import starwars.entities.Canteen;
import starwars.entities.actors.behaviors.AttackInformation;
import starwars.entities.actors.behaviors.AttackNeighbours;
import starwars.entities.actors.behaviors.DrinkOrDrop;
import starwars.entities.actors.behaviors.Patrol;
import starwars.entities.actors.behaviors.takeCanteens;
import starwars.entities.actors.behaviors.DropItem;
import starwars.entities.actors.behaviors.PickLightsaber;

/**
 * Ben (aka Obe-Wan) Kenobi.  
 * 
 * At this stage, he's an extremely strong critter with a <code>Lightsaber</code>
 * who wanders around in a fixed pattern and neatly slices any Actor not on his
 * team with his lightsaber.
 * 
 * Note that you can only create ONE Ben, like all SWLegends.
 * @author rober_000
 *
 */
public class BenKenobi extends SWLegend {

	private static BenKenobi ben = null; // yes, it is OK to return the static instance!
	private Patrol path;
	private BenKenobi(MessageRenderer m, SWWorld world, Direction [] moves) {
		super(Team.GOOD, 1000, m, world);
		path = new Patrol(moves);
		this.setShortDescription("Ben Kenobi");
		this.setLongDescription("Ben Kenobi, an old man who has perhaps seen too much");
		LightSaber bensweapon = new LightSaber(m);
		
		// Added Leave affordance so Ben can drop his lightsaber...
		bensweapon.addAffordance(new Leave(bensweapon, m));
		setItemCarried(bensweapon);
		
	}

	public static BenKenobi getBenKenobi(MessageRenderer m, SWWorld world, Direction [] moves) {
		ben = new BenKenobi(m, world, moves);
		ben.activate();
		return ben;
	}
	
	public void dropItem() {
		AttackInformation carriedItem;
		carriedItem = DropItem.findCarriedItem(ben);
		say(getShortDescription() + " drops whatever he's holding");
		scheduler.schedule(carriedItem.affordance, ben, 1);
		
	}
	public void pickWeapon() {
		// Ben Looks for his lightsaber to pick up, if he can't find it continue patrolling
		AttackInformation weapon;
		weapon = PickLightsaber.findLightsaber(ben, ben.world);
		if (weapon.affordance == null) {
			say(getShortDescription() + "looks confused. \"Where did I leave it?\"");
			Direction newdirection = path.getNext();
			say(getShortDescription() + " moves " + newdirection);
			Move myMove = new Move(newdirection, messageRenderer, world);
			scheduler.schedule(myMove, this, 1);
		}
		say(getShortDescription() + " picks up his lightsaber");
		scheduler.schedule(weapon.affordance, ben, 1);
	}
	public void drinkOrDrop() {
		//drink or drop the canteen if it is empty
		AttackInformation carriedItem;
		carriedItem = DrinkOrDrop.findDrinkable(ben);
		scheduler.schedule(carriedItem.affordance, ben, 1);
	}
	

	@Override
	protected void legendAct() {
		
		if(isDead()) {
			return;
		}
		// Note attacking should STILL come before drinking
		// Else Ben will drink halfway into the fight!
		AttackInformation attack;
		attack = AttackNeighbours.attackLocals(ben,  ben.world, true, true);
		
		if (attack != null) {
			say(getShortDescription() + " suddenly looks sprightly and attacks " +
		attack.entity.getShortDescription());
			scheduler.schedule(attack.affordance, ben, 1);
		}
		else  {
			// New code where Ben drops his weapon, and
			// picks up a canteen and drinks from it if his hitpoints < 100%
			// Ben drinks if he is holding a canteen and is <100% hp
			// Ben drops the canteen if he is at 100%
			// If Ben isn't holding anything he picks up his lightsaber(if it's there)
			// Else he moves
			if (ben.getItemCarried() instanceof Canteen) {
				if (getHitpoints() < 1000) {
					drinkOrDrop();
					return;
				}
				else {
					dropItem();
					return;
				}
			}
			if (getHitpoints() < 1000) {
				AttackInformation canteens;
				canteens = takeCanteens.findCanteens(ben, ben.world);
				if (canteens != null) {
					if (ben.getItemCarried() == null) {
						say(getShortDescription() + " picks up " + canteens.entity.getShortDescription());
						scheduler.schedule(canteens.affordance, ben, 1);
						return;
					}
					else {
					dropItem();
					return;
					}	
				}
			}
			if (ben.getItemCarried() == null) {
				pickWeapon();
				return;
			}		
			Direction newdirection = path.getNext();
			say(getShortDescription() + " moves " + newdirection);
			Move myMove = new Move(newdirection, messageRenderer, world);
			scheduler.schedule(myMove, this, 1);
			
		}
	}

}
